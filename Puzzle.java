package puzzle;
import java.util.ArrayList;
import java.util.LinkedList;
import puzzleException.*;
public class Puzzle{
    ArrayList<ArrayList<Cuadro>> alc;
    LinkedList<Integer> lli;
    private int tam;
    private int posX,posY;
    private boolean mueveDerecha,mueveIzquierda,mueveArriba,mueveAbajo;
    private int expecto;
    
    public Puzzle(int tam,int...a){
        inicializa();
        this.tam=tam;
        for(int i:a){
            lli.add(i);
        }
        for(int i=0;i<tam;i++){
            alc.add(new ArrayList());
            for(int j=0;j<tam;j++){
                int x = lli.removeFirst();
                if (x==0){posX=i;posY=j;}
                alc.get(i).add(new Cuadro(x,i,j));
            }
        }
        for(int i:lli){
            expecto *= 10;
            expecto += i;
        }lli.add(this.toInt());
    }
    final void inicializa(){
        lli = new LinkedList();
        alc=new ArrayList();
        expecto=0;
        setMovimientos(posX==1,posX==0,posY==0,posY==1);
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
    
    public String heuristico(){
        return "";
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
            if(lli.contains(this.toInt())){
                System.out.println("No se puede llegar al resultado");
                return "No se puede llegar al resultado";
            }else{lli.add(this.toInt());}
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
            }else if(mueveAbajo && mueveIzquierda){
                izquierda();
            }else if(mueveIzquierda && mueveArriba){
                arriba();
            }else{
                derecha();
            }
            if(lli.contains(this.toInt())){
                System.out.println("No se puede llegar al resultado");
                return "No se puede llegar al resultado";
            }else{lli.add(this.toInt());}
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
}