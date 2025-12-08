package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardModel {
    // 储存棋盘上所有的棋子，要实现吃子的话，直接通过pieces.remove(被吃掉的棋子)删除就可以
    private final List<Piece> allPieces;
    private final int X;
    private final int Y;

    public ChessBoardModel() {
        allPieces = new ArrayList<>();
        initializePieces();
        X = 9;
        Y = 10;
    }

    public static int getX() {
        return 9;
    }

    public static int getY() {
        return 10;
    }

    private void initializePieces() {
        PieceFactory pieceFactory = new PieceFactory();
        allPieces.addAll(pieceFactory.PiecesI());
    }

    public List<Piece> getPieces() {
        return allPieces;
    }

    public Piece getPieceAt(int x, int y) {
        for (Piece piece : allPieces) {
            if (piece.getPosition().getX() == x && piece.getPosition().getY() == y) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 10;
    }

    public boolean movePiece(Piece piece, int newX, int newY,List<Piece> allPieces) {
        if (!isValidPosition(newX, newY)) {
            return false;
        }

        if (!piece.canMoveTo(newX, newY,allPieces)){
            return false;
        }

        Piece targetPiece = getPieceAt(newX, newY);
        if (targetPiece != null) {
            if (piece.isEnemy(targetPiece)) {
                targetPiece.beEaten(piece);
                allPieces.remove(targetPiece);
            }
            else{
                return false;
            }
        }

        piece.moveTo(newX, newY, allPieces);
        return true;
    }

}
