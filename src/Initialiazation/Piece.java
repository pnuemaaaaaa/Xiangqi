package Initialiazation;


import java.util.List;

public abstract class Piece {
    private String name;
    private Position position;
    private Player player;
    private boolean alive = true;

    public Piece(){}

    public Piece(String name,Player player){
        this.name=name;
        this.player=player;
    }
    public Piece(String name, Position position, Player player) {
        this.name = name;
        this.position = position;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Piece getPiece(String name){
        if(name.equals(this.name)){
            return this;
        }
        else{
            return null;
        }
    }

    public abstract boolean canMoveTo(int newX, int newY, List<Piece> allPieces);

    public abstract void moveTo(int newX, int newY,List<Piece> allPieces);

    public abstract boolean canBeEaten(Piece p);

    public abstract void beEaten(Piece p);

    // 新增辅助方法
    protected Piece getPieceAt(int x, int y, List<Piece> allPieces){
        for(Piece p : allPieces){
            if(p.isAlive() && p.getPosition()!=null&&
                    p.getPosition().getX() == x && p.getPosition().getY() == y){
                return p;
            }
        }
        return null;
    }

    protected boolean isStraightLine(int x1, int y1, int x2, int y2) {
        return x1 == x2 || y1 == y2;
    }

    protected boolean hasPieceBetween(int x1, int y1, int x2, int y2, List<Piece> allPieces) {
        if(!isStraightLine(x1,y1,x2,y2)){
            return false;
        }

        // 确定方向
        int dx = (x2 > x1) ? 1 : (x2 < x1) ? -1 : 0;
        int dy = (y2 > y1) ? 1 : (y2 < y1) ? -1 : 0;

        // 从起点后一个位置开始检查
        int currentX = x1 + dx;
        int currentY = y1 + dy;

        // 遍历直到终点前一个位置
        while (currentX != x2 || currentY != y2) {
            if (getPieceAt(currentX, currentY, allPieces) != null) {
                return true;
            }
            currentX += dx;
            currentY += dy;
        }
        return false;
    }

    protected boolean isEnemy(Piece p){
        if(p.getPlayer() == this.player){
            return false;
        }
        else{
            return true;
        }
    }

    protected int getCurrentX() {
        return position != null ? position.getX() : -1;
    }

    protected int getCurrentY() {
        return position != null ? position.getY() : -1;
    }

    protected boolean isWithinBoard(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 10;
    }

    protected boolean isSamePosition(int x, int y) {
        return position != null && position.getX() == x && position.getY() == y;
    }

    protected boolean isSamePlayer(Piece p) {
        return p != null && this.player == p.getPlayer();
    }

    // 计算两点之间的直线距离（用于车、炮等的移动检查）
    protected int getStraightDistance(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y2 - y1); // 垂直移动
        } else if (y1 == y2) {
            return Math.abs(x2 - x1); // 水平移动
        }
        return -1; // 不是直线
    }


}
