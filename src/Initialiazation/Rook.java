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
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 检查是否直线移动
        int distance = getStraightDistance(currentX, currentY, newX, newY);
        if (distance < 0) return false; // 不是直线移动

        // 3. 检查路径上是否有棋子阻挡（除了目标位置）
        // 注意：这里需要传入所有棋子的列表来检查

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
        // 车可以被任何敌方棋子吃掉
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        setPosition(null);
    }
}
