package Initialiazation;

import java.util.ArrayList;
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
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;
        if (getPieceAt(newX,newY,allPieces) != null &&
                getPieceAt(newX,newY,allPieces).getPlayer() == this.getPlayer() ) return false;

        // 2. 检查是否在九宫格内
        if (!isInPalace(newX, newY)) return false;

        // 3. 仕只能走斜线一步（对角线）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        return dx == 1 && dy == 1 && isInPalace(newX,newY);
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

    @Override
    public List<Position> getPossibleMoves(List<Piece> allPieces) {
        List<Position> moves = new ArrayList<>();

        // 仕只能走斜线一步，只能在九宫内的5个点上
        int[][] directions = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        Position currentPos = this.getPosition();
        int x = currentPos.getX();
        int y = currentPos.getY();

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (canMoveTo(newX, newY, allPieces)) {
                moves.add(new Position(newX, newY));
            }
        }

        return moves;
    }

}