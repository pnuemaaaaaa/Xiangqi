package Initialiazation;

import java.util.List;

public class King extends Piece {

    public King(Position position, Player player) {
        // 根据玩家决定名称：红方是"帅"，黑方是"将"
        super(player == Player.RED ? "帅" : "將",
                position,
                player);
    }

    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 检查是否在棋盘内
        if (!isWithinBoard(newX, newY)) return false;

        // 2. 检查是否是自己当前位置
        if (isSamePosition(newX, newY)) return false;

        // 3. 检查是否在九宫格内
        if (!isInPalace(newX, newY)) return false;

        // 4. 检查移动距离：只能走一步（上下左右）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private boolean isInPalace(int x, int y) {
        // 红方九宫：x:3-5, y:0-2
        // 黑方九宫：x:3-5, y:7-9
        if (getPlayer() == Player.RED) {
            return x >= 3 && x <= 5 && y >= 0 && y <= 2;
        } else {
            return x >= 3 && x <= 5 && y >= 7 && y <= 9;
        }
    }

    @Override
    public void moveTo(int newX, int newY, List<Piece> allPieces) {
        if (canMoveTo(newX, newY,allPieces)) {
            setPosition(new Position(newX, newY));
        }
    }

    @Override
    public boolean canBeEaten(Piece p) {
        // 将/帅可以被任何敌方棋子吃掉（除了将帅不能对面直接吃）
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        // 被吃掉后，position设为null
        setPosition(null);
    }
}
