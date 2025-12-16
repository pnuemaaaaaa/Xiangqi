package Initialiazation;

import java.util.List;

public class ChessBoardModel {
    private List<Piece> allPieces;
    private Player currentPlayer;
    private PieceFactory pieceFactory;
    private static final int Rows = 10;
    private static final int Cols = 9;

    public ChessBoardModel(){
        initializeChessBoard();
    }
    public void initializeChessBoard() {
        PieceFactory pieceFactory = new PieceFactory();
        allPieces = pieceFactory.Pieces();
        currentPlayer = Player.BLACK;
    }//重启游戏也可以用

    //player
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public static int getRows() {
        return Rows;
    }

    public static int getCols() {
        return Cols;
    }

    public List<Piece> getAllPieces() {
        return allPieces;
    }

    public void changePlayer(){
        if(currentPlayer == Player.RED){
            currentPlayer = Player.BLACK;
        }else{
            currentPlayer = Player.RED;
        }
    }

    public boolean isValidPosition(int x, int y){
        return x >= 0 && y >= 0 && x < Cols && y < Rows;
    }

    public Piece getPieceAt(int x, int y) {
        for (Piece piece : allPieces) {
            Position pos = piece.getPosition();
            if (pos.getX() == x && pos.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    //添加局面限制，棋子的移动要考虑特殊情况、当前持方
    public void movePiece(Piece piece1,Piece piece2, int x, int y){
        if(currentPlayer != piece1.getPlayer()){
            return;
        }else{
            piece1.moveTo(x,y,allPieces);
            changePlayer();
        }
    }

}
