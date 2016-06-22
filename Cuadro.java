package puzzle;

public class Cuadro {
    int num;
    int posX,posY;
    Cuadro(int num,int posX,int posY){
        this.num=num;this.posX=posX;this.posY=posY;
    }
    public void setNum(int num) {this.num = num;}
    public void setPosX(int posX) {this.posX = posX;}
    public void setPosY(int posY) {this.posY = posY;}
    public int getNum() {return num;}
    public int getPosX() {return posX;}
    public int getPosY() {return posY;}
}