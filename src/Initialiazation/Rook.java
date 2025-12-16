package Initialiazation;

import java.util.List;

public class Rook extends Piece {

    public Rook(Position position, Player player) {
        // 红方是"车"，黑方是"車"（写法不同）
        super(player == Player.RED ? "车" : "車",
                position,
                player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;
        if (getPieceAt(newX,newY,allPieces) != null &&
                getPieceAt(newX,newY,allPieces).getPlayer() == this.getPlayer() ) return false;

        // 2. 检查是否直线移动
        int nowX = this.getPosition().getX();
        int nowY = this.getPosition().getY();

        if (!isStraightLine(nowX,nowY,newX,newY)){
            return false;
        }

        // 3. 检查移动路径上是否有其他棋子阻挡（不包含目标位置）
        if (countPieceBetween(currentX, currentY, newX, newY, allPieces)!=0) {
            return false; // 路径上有棋子阻挡
        }

        return true;
    }

    @Override
    public void moveTo(int newX, int newY, List<Piece> allPieces) {
        if (canMoveTo(newX, newY, allPieces)) {
            Piece targetPiece = getPieceAt(newX,newY,allPieces);
            eat(targetPiece,allPieces);
            setPosition(new Position(newX, newY));
        }
    }

    @Override
    public boolean canEat(Piece p, List<Piece> allPieces) {
        return true;
    }

    @Override
    public void eat(Piece p, List<Piece> allPieces) {
        allPieces.remove(p);
    }

}
