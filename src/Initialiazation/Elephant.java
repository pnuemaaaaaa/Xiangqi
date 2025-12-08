package Initialiazation;

import java.util.List;

public class Elephant extends Piece {

    public Elephant(Position position, Player player) {
        // 红方是"相"，黑方是"象"
        super(player == Player.RED ? "相" : "象",
                position,
                player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 检查是否过河
        if (!isOnSameSide(newY)) return false;

        // 3. 检查是否走"田"字（对角移动2格）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        if (dx != 2 || dy != 2) return false;
        // 4. 检查是否"塞象眼"（象眼位置是否有棋子）
        // 象眼是(currentX, currentY)和(newX, newY)的中点
        int eyeX = (currentX + newX) / 2;
        int eyeY = (currentY + newY) / 2;
        // 注意：这里需要传入所有棋子的列表来检查，但父类没有这个方法
        // 我们暂时返回true，实际游戏中需要传入棋子列表
        if (getPieceAt(eyeX, eyeY, allPieces) != null) return false;

        Piece targetPiece = getPieceAt(newX, newY, allPieces);
        if (targetPiece != null) {
            return isEnemy(targetPiece) && targetPiece.canBeEaten(this);
        }

        return true;
    }

    private boolean isOnSameSide(int newY) {
        // 红方象不能过河（y <= 4）
        // 黑方象不能过河（y >= 5）
        if (getPlayer() == Player.RED) {
            return newY <= 4;
        } else {
            return newY >= 5;
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
        // 象可以被任何敌方棋子吃掉
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        setPosition(null);
    }

}
