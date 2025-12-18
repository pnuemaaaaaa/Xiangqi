package Initialiazation;

public class Position {
    private int x =-1;
    private int y =-1;
    //x是横坐标，y是纵坐标

    public Position(){}
    public Position(int x, int y){
        if(x>=0 && y>=0 && x<9 && y<10) {
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid(){
        if(x>=0 && y>=0 && x<9 && y<10) {
            return true;
        }else{
            return false;
        }
    }
}
