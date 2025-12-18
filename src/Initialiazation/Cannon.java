package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Position position, Player player) {
        // 炮不分红黑名称，都是"炮"
        super("炮", position, player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        // 1. 基本检查
        if (!isWithinBoard(newX, newY)) {
            return false;
        }
        if (isSamePosition(newX, newY)) {
            return false;
        }

        // 2. 检查是否直线移动
        int nowX = this.getPosition().getX();
        int nowY = this.getPosition().getY();

        if (!isStraightLine(nowX, nowY, newX, newY)) {
            return false;
        }
        // 3. 分叉：目标位置有无棋子
        Piece targetPosPiece = getPieceAt(newX, newY, allPieces);

        //3.1 无棋子（暂时不考虑将帅照面的情况）
        if (targetPosPiece == null) {
            return countPieceBetween(nowX, nowY, newX, newY, allPieces) == 0;
        }

        //3.2 有棋子（暂时不考虑将帅照面的情况）
        if (targetPosPiece.getPlayer() != null) {
            return canEat(targetPosPiece, allPieces);
        }

        return false;
    }


    @Override
    public void moveTo(int newX, int newY, List<Piece> allPieces) {
        if (canMoveTo(newX, newY, allPieces)) {
            Piece targetPiece = getPieceAt(newX, newY, allPieces);
            eat(targetPiece, allPieces);
            this.setPosition(new Position(newX, newY));
        }
    }

    @Override
    public boolean canEat(Piece piece, List<Piece> allPieces) {
        int nowX = this.getPosition().getX();
        int nowY = this.getPosition().getY();
        int newX = piece.getPosition().getX();
        int newY = piece.getPosition().getY();
        if (piece.getPlayer() == this.getPlayer()) {
            return false;
        } else {
            if (countPieceBetween(nowX, nowY, newX, newY, allPieces) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void eat(Piece piece, List<Piece> allPieces) {
        allPieces.remove(piece);
    }

    @Override
    public List<Position> getPossibleMoves(List<Piece> allPieces) {
        List<Position> moves = new ArrayList<>();
        for(int x=0;x<9;x++){
            for(int y=0;y<10;y++){
                if(canMoveTo(x,y,allPieces)){
                    moves.add(new Position(x,y));
                }
            }
        }
        return moves;
    }
}