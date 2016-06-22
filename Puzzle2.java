package puzzle;
import java.util.ArrayList;
import java.util.LinkedList;
import puzzleException.*;
/**
 *
 * @author dominios
 */
public class Puzzle2{
    ArrayList<ArrayList<Cuadro>> alc;
    LinkedList<Integer> lli;
    LinkedList<Integer> lliPasados;
    private int tam,tam2;
    private int posX,posY;
    private boolean mueveDerecha,mueveIzquierda,mueveArriba,mueveAbajo;
    private int expecto;
    
    public Puzzle2(int tam,int...a){
        this.tam=tam;
        inicializa();
        for(int i:a){
            lli.add(i);
        }
        setPuzzle();
    }
    public Puzzle(int tam,LinkedList<Integer> lli){
        this.tam=tam;
        inicializa();
        this.lli=lli;
        setPuzzle();
    }
    public void setPuzzle(){
        for(int i=0;i<tam;i++){
            alc.add(new ArrayList());
            for(int j=0;j<tam;j++){
                int x = lli.removeFirst();
                if (x==0){posX=j;posY=i;}
                alc.get(i).add(new Cuadro(x,j,i));
            }
        }
        for(int i:lli){
            expecto *= 10;
            expecto += i;
        }lliPasados.add(this.toInt());
    }
    final void inicializa(){
        tam2=tam*tam;
        lli = new LinkedList();
        lliPasados = new LinkedList();
        alc=new ArrayList();
        expecto=0;
        setMovimientos(posX!=(tam-1),posX!=0,posY!=0,posY!=(tam-1));
    }
    void setMovimientos(boolean a,boolean b,boolean c,boolean d){
        mueveDerecha=a;mueveIzquierda=b;
        mueveArriba=c;mueveAbajo=d;
    }
    
    public void izquierda(){
        alc.get(posX).get(posY).setNum(alc.get(posX).get(posY-1).getNum());
        posY--;
        alc.get(posX).get(posY).setNum(0);
        setMovimientos(true,false,mueveArriba,mueveAbajo);
    }
    public void derecha(){
        alc.get(posX).get(posY).setNum(alc.get(posX).get(posY+1).getNum());
        posY++;
        alc.get(posX).get(posY).setNum(0);
        setMovimientos(false,true,mueveArriba,mueveAbajo);
    }
    public void arriba(){
        alc.get(posX).get(posY).setNum(alc.get(posX-1).get(posY).getNum());
        posX--;
        alc.get(posX).get(posY).setNum(0);
        setMovimientos(mueveDerecha,mueveIzquierda,false,true);
    }
    public void abajo(){
        alc.get(posX).get(posY).setNum(alc.get(posX+1).get(posY).getNum());
        posX++;
        alc.get(posX).get(posY).setNum(0);
        setMovimientos(mueveDerecha,mueveIzquierda,true,false);
    }
    
    public String gloton(){
        if(this.toInt()==expecto){
            return this.toString();
        }else{
            if(mueveArriba){arriba();if(!lliPasados.contains(this.toInt())){
                lliPasados.add(this.toInt());
                if(this.toInt()==expecto){return this.toString();}
            }abajo();}
            if(mueveDerecha){derecha();;if(!lliPasados.contains(this.toInt())){
                lliPasados.add(this.toInt());
                if(this.toInt()==expecto){return this.toString();}
            }izquierda();}
            if(mueveAbajo){abajo();;if(!lliPasados.contains(this.toInt())){
                lliPasados.add(this.toInt());
                if(this.toInt()==expecto){return this.toString();}
            }arriba();}
            if(mueveIzquierda){izquierda();if(!lliPasados.contains(this.toInt())){
                lliPasados.add(this.toInt());
                if(this.toInt()==expecto){return this.toString();}
            }derecha();}
        }
        System.out.println(this);
        lliPasados.removeFirst();
        toPuzzle(lliPasados.peek());
        return heuristico();
    }
    
    public String deterministicoCW()throws PuzzleException{
        if (tam>2){throw new MetodoInapropiadoException();}
        if(this.toInt()==expecto){
            return this.toString();
        }else{
            if(mueveDerecha && mueveAbajo){
                derecha();
            }else if(mueveAbajo && mueveIzquierda){
                abajo();
            }else if(mueveIzquierda && mueveArriba){
                izquierda();
            }else{
                arriba();
            }
            System.out.println(this);
            if(lliPasados.contains(this.toInt())){
                System.out.println("No se puede llegar al resultado");
                return "No se puede llegar al resultado";
            }else{lliPasados.add(this.toInt());}
            return deterministicoCW();
        }
    }
    public String deterministicoCCW()throws PuzzleException{
        if (tam>2){throw new MetodoInapropiadoException();}
        if(this.toInt()==expecto){
            return this.toString();
        }else{
            if(mueveDerecha && mueveAbajo){
                abajo();
            }else if(mueveDerecha && mueveArriba){
                derecha();
            }else if(mueveIzquierda && mueveArriba){
                arriba();
            }else{
                izquierda();
            }
            if(lliPasados.contains(this.toInt())){
                System.out.println("No se puede llegar al resultado");
                return "No se puede llegar al resultado";
            }else{lliPasados.add(this.toInt());}
            System.out.println(this);
            return deterministicoCCW();
        }
    }
    
    @Override
    public String toString(){
        return alc.get(0).get(0).getNum()+""+alc.get(0).get(1).getNum()+"\n"
               +alc.get(1).get(0).getNum()+alc.get(1).get(1).getNum()+"\n";
    }
    public int toInt(){
        int buffer;
        buffer = alc.get(1).get(1).getNum()
                +10*(alc.get(1).get(0).getNum()
                +10*(alc.get(0).get(1).getNum()
                +10*(alc.get(0).get(0).getNum())));
        return buffer;
    }
    public void toPuzzle(Integer x){
        Puzzle p;
        LinkedList<Integer> lli=new LinkedList();
        for(int i=0;i<tam2;i++){
            lli.addFirst(x%10);
            x/=10;
        }
        setPuzzle();
    }
}