package Initialiazation;

import java.util.ArrayList;
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
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();

        // 1. 基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;
        if (getPieceAt(newX,newY,allPieces) != null &&
                getPieceAt(newX,newY,allPieces).getPlayer() == this.getPlayer() ) return false;

        // 2. 检查是否过河
        if (!isOnSameSide(newY)) return false;

        // 3. 检查是否走"田"字（对角移动2格）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        if (dx != 2 || dy != 2) return false;
        // 4. 检查是否"塞象眼"（象眼位置是否有棋子）
        int eyeX = (currentX + newX) / 2;
        int eyeY = (currentY + newY) / 2;
        if (getPieceAt(eyeX, eyeY, allPieces) != null) return false;
        //5.不能过河
        if(!isOnSameSide(newY)) return false;

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
        List<Position> possibleMoves = new ArrayList<>();
        int nowX = this.getPosition().getX();
        int nowY = this.getPosition().getY();
        Position pos1 = new Position(nowX+2,nowY+2);
        Position pos2 = new Position(nowX-2,nowY+2);
        Position pos3 = new Position(nowX+2,nowY-2);
        Position pos4 = new Position(nowX-2,nowY-2);
        possibleMoves.add(pos1);
        possibleMoves.add(pos2);
        possibleMoves.add(pos3);
        possibleMoves.add(pos4);
        for(Position pos : possibleMoves){
            if(pos.isValid() && canMoveTo(pos.getX(),pos.getY(),allPieces)){
                moves.add(pos);
            }
        }
        return moves;

    }

}
