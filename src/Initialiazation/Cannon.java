package Initialiazation;

import java.util.List;

public class Cannon extends Piece {

    public Cannon(Position position, Player player) {
        // 炮不分红黑名称，都是"炮"
        super("炮", position, player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = getCurrentX();
        int currentY = getCurrentY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 检查是否直线移动
        int distance = getStraightDistance(currentX, currentY, newX, newY);
        if (distance < 0) return false;

        // 3. 炮的特殊规则：
        //    - 如果目标位置没有棋子：移动时路径上不能有任何棋子
        //    - 如果目标位置有棋子（吃子）：路径上必须刚好有一个棋子（作为炮架）
        // 注意：这里需要传入所有棋子的列表来检查
        Piece targetPiece = getPieceAt(newX,newY,allPieces);

        if (targetPiece == null) {
            return !hasPieceBetween(currentX,currentY,newX,newY,allPieces);
        }
        else{
            if (!isEnemy(targetPiece) || !targetPiece.canBeEaten(this)) return false;

            int pieceCount = 0;
            if (currentX == newX) { // 垂直移动
                int startY = Math.min(currentY, newY);
                int endY = Math.max(currentY, newY);
                for (int y = startY + 1; y < endY; y++) {
                    if (getPieceAt(currentX, y, allPieces) != null) {
                        pieceCount++;
                    }
                }
            } else { // 水平移动
                int startX = Math.min(currentX, newX);
                int endX = Math.max(currentX, newX);
                for (int x = startX + 1; x < endX; x++) {
                    if (getPieceAt(x, currentY, allPieces) != null) {
                        pieceCount++;
                    }
                }
            }
            return pieceCount == 1;
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
        // 炮可以被任何敌方棋子吃掉
        return p != null && !isSamePlayer(p);
    }

    @Override
    public void beEaten(Piece p) {
        setPosition(null);
    }
}
