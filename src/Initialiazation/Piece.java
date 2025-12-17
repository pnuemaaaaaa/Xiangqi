package Initialiazation;


import java.util.List;

public abstract class Piece {
    private String name;
    private Position position;
    private Player player;

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

    public Piece getPiece(String name){
        if(name.equals(this.name)){
            return this;
        }
        else{
            return null;
        }
    }

    //判断合法移动
    public abstract boolean canMoveTo(int newX, int newY, List<Piece> allPieces);

    //试图移动
    public abstract void moveTo(int newX, int newY,List<Piece> allPieces);

    //吃棋子
    public abstract boolean canEat(Piece piece,List<Piece> allPieces);
    public abstract void eat(Piece piece,List<Piece>allPieces);//从列表中删除即可

    //新增辅助方法
    //判断目标位置有无棋子
    protected Piece getPieceAt(int x, int y, List<Piece> allPieces) {
        for (Piece piece : allPieces) {
            Position pos = piece.getPosition();
            if (pos.getX() == x && pos.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    //判断移动路径（仅限横竖）的直线上有无棋子
    protected boolean isStraightLine(int x1, int y1, int x2, int y2) {
        return x1 == x2 || y1 == y2;
    }
    protected int countPieceBetween(int x1, int y1, int x2, int y2, List<Piece> allPieces) {
        int num = 0;
        // 确定方向
        int dx = (x2 > x1) ? 1 : (x2 < x1) ? -1 : 0;
        int dy = (y2 > y1) ? 1 : (y2 < y1) ? -1 : 0;

        // 从起点后一个位置开始检查
        int currentX = x1 + dx;
        int currentY = y1 + dy;

        // 遍历直到终点前一个位置
        while (currentX != x2 || currentY != y2) {
            if (getPieceAt(currentX, currentY, allPieces) != null) {
                num++;
            }
            currentX += dx;
            currentY += dy;
        }
        return num;
    }
    protected int hasPieceBetweenColNumbers(int x, int y1, int y2, List<Piece> allPieces) {
        int sum =0;
        // 确定方向
        int dy = (y2 > y1) ? 1 : (y2 < y1) ? -1 : 0;

        // 从起点后一个位置开始检查
        int currentX = x;
        int currentY = y1 + dy;

        // 遍历直到终点前一个位置
        while (currentY != y2) {
            if (getPieceAt(currentX, currentY, allPieces) != null) {
                sum++;
            }
            currentY += dy;
        }
        return sum;
    }

    //判断是否为敌方棋子
    protected boolean isEnemy(Piece p){
        if(p.getPlayer() == this.player){
            return false;
        }
        else{
            return true;
        }
    }

    protected boolean isWithinBoard(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 10;
    }

    protected boolean isSamePosition(int x, int y) {
        return x == this.position.getX() && y == this.position.getY();
    }

    //高效移动计算方法
    public abstract List<Position> getPossibleMoves(List<Piece> allPieces);
    protected boolean isInBoard(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 10;
    }

}
