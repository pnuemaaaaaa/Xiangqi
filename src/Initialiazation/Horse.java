package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    public Horse(Position position, Player player) {
        super(player == Player.RED ? "马" : "馬", position, player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 检查是否走"日"字
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        // 马的走法：一个方向走1格，另一个方向走2格
        if (!((dx == 1 && dy == 2) || (dx == 2 && dy == 1))) return false;

        // 3. 检查是否"蹩马腿"
        // 如果dx=1, dy=2：检查垂直方向的"马腿"
        // 如果dx=2, dy=1：检查水平方向的"马腿"
        // 注意：这里需要传入所有棋子的列表来检查，暂时返回true

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

        // 4. 检查目标位置
        Piece targetPiece = getPieceAt(newX, newY, allPieces);
        if (targetPiece != null) {
            // 有棋子，必须是敌方且可被吃掉
            return isEnemy(targetPiece) && targetPiece.canBeEaten(this);
        }

        return true;
    }

    @Override
    public void moveTo(int newX, int newY, List<Piece> allPieces) {
        if (canMoveTo(newX, newY, allPieces)) {
            setPosition(new Position(newX, newY));
        }
    }

    @Override
    public boolean canBeEaten(Piece p) {
        // 马可以被任何敌方棋子吃掉
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        setPosition(null);
    }
}