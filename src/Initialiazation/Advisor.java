package Initialiazation;

import java.util.List;

public class Advisor extends Piece {

    public Advisor(Position position, Player player) {
        // 红方是"仕"，黑方是"士"
        super(player == Player.RED ? "仕" : "士",
                position,
                player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 检查是否在九宫格内
        if (!isInPalace(newX, newY)) return false;

        // 3. 仕只能走斜线一步（对角线）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        return dx == 1 && dy == 1;
    }

    private boolean isInPalace(int x, int y) {
        // 九宫格：x:3-5, 红方y:0-2, 黑方y:7-9
        if (getPlayer() == Player.RED) {
            return x >= 3 && x <= 5 && y >= 0 && y <= 2;
        } else {
            return x >= 3 && x <= 5 && y >= 7 && y <= 9;
        }
    }

    @Override
    public void moveTo(int newX, int newY, List<Piece> allPieces) {
        if (canMoveTo(newX, newY, allPieces)) {
            setPosition(new Position(newX, newY));
        }
    }

    @Override
    public boolean canBeEaten(Piece p) {
        // 仕可以被任何敌方棋子吃掉
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        setPosition(null);
    }

}