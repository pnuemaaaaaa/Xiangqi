package Initialiazation;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(Position position, Player player) {
        // 红方是"兵"，黑方是"卒"
        super(player == Player.RED ? "兵" : "卒",
                position,
                player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;

        // 2. 计算移动距离（兵只能走一步）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        if (dx + dy != 1) return false;
        // 3. 检查移动方向
        if (getPlayer() == Player.RED) {
            // 红方兵：过河前只能向前（y增加），过河后可以向前或左右
            if (currentY < 5) { // 未过河
                return newY == currentY + 1 && newX == currentX;
            } else { // 已过河
                return (newY == currentY + 1 && newX == currentX) || // 向前
                        (newY == currentY && Math.abs(newX - currentX) == 1); // 左右
            }
        } else {
            // 黑方卒：过河前只能向前（y减少），过河后可以向前或左右
            if (currentY > 4) { // 未过河
                return newY == currentY - 1 && newX == currentX;
            } else { // 已过河
                return (newY == currentY - 1 && newX == currentX) || // 向前
                        (newY == currentY && Math.abs(newX - currentX) == 1); // 左右
            }
        }
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
