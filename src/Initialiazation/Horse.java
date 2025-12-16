package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    public Horse(Position position, Player player) {
        super(player == Player.RED ? "马" : "馬", position, player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;
        if (getPieceAt(newX,newY,allPieces) != null &&
                getPieceAt(newX,newY,allPieces).getPlayer() == this.getPlayer() ) return false;

        // 2. 检查是否走"日"字
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);
        if (!((dx == 1 && dy == 2) || (dx == 2 && dy == 1))) return false;

        // 3. 检查是否"蹩马腿"
        int legX = currentX;
        int legY = currentY;
        if (dx == 1 && dy == 2) {
            // 垂直"日"字：马腿在垂直方向
            legY = currentY + (newY - currentY) / 2;
        } else if (dx == 2 && dy == 1) {
            // 水平"日"字：马腿在水平方向
            legX = currentX + (newX - currentX) / 2;
        }
        if (getPieceAt(legX, legY, allPieces) != null) return false;

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