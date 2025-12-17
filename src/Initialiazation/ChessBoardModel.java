package Initialiazation;

import java.util.ArrayList;
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
    public void movePiece(Piece piece1,Piece piece2,int x, int y){
        // 1. 检查游戏是否已经结束
        if (isCheckmate() || isStalemate()) {
            System.out.println("游戏已结束！");
            return;
        }

        // 2. 检查是否轮到当前玩家
        if (piece1.getPlayer() != currentPlayer) {
            System.out.println("不是你的回合！");
            return;
        }

        // 3. 检查移动是否基本合法
        if (!piece1.canMoveTo(x, y, allPieces)) {
            System.out.println("移动不合法！");
            return;
        }

        // 5. 执行移动（吃子）
        if (piece2!= null) {
            piece1.eat(piece2, allPieces);
            piece1.setPosition(new Position(x, y));
            changePlayer();
        }
    }

    //将军检测-当前
    public boolean isKingInCheck() {
        // 找到当前玩家的帅/将
        Piece currentKing = findKing(currentPlayer);
        if (currentKing == null) {
            return false; // 理论上不应该发生
        }

        // 检查是否有敌方棋子可以攻击到帅/将的位置
        return isPositionUnderAttack(currentKing.getPosition().getX(),
                currentKing.getPosition().getY(),
                currentPlayer);
    }
    public Piece findKing(Player player) {
        for (Piece piece : allPieces) {
            String name = piece.getName();
            if ((player == Player.RED && name.equals("帅")) ||
                    (player == Player.BLACK && name.equals("将"))) {
                return piece;
            }
        }
        return null;
    }
    public boolean isPositionUnderAttack(int x, int y, Player defendingPlayer) {
        Player attackingPlayer = (defendingPlayer == Player.RED) ? Player.BLACK : Player.RED;

        for (Piece piece : allPieces) {
            // 只检查敌方棋子
            if (piece.getPlayer() == attackingPlayer) {
                // 如果棋子可以移动到目标位置（意味着可以攻击）
                if (piece.canMoveTo(x, y, allPieces)) {
                    return true;
                }
            }
        }
        return false;
    }

    //将军检测-预测-为将死检测服务
    public boolean willMoveCauseCheck(Piece piece, int newX, int newY) {
        // 保存当前状态
        Position originalPos = piece.getPosition();
        Piece targetPiece = getPieceAt(newX, newY);

        // 临时执行移动
        if (targetPiece != null) {
            allPieces.remove(targetPiece);
        }
        piece.setPosition(new Position(newX, newY));

        // 检查移动后是否被将军
        boolean inCheck = isKingInCheck();

        // 恢复状态
        piece.setPosition(originalPos);
        if (targetPiece != null) {
            allPieces.add(targetPiece);
        }

        return inCheck;
    }

    //将死检测
    public boolean isCheckmate() {
        // 1. 首先检查是否被将军
        if (!isKingInCheck()) {
            return false;
        }

        // 2. 检查当前玩家是否有任何合法的移动
        for (Piece piece : allPieces) {
            if (piece.getPlayer() == currentPlayer) {
                // 使用高效的getLegalMoves方法
                List<Position> legalMoves = getLegalMoves(piece);
                if (!legalMoves.isEmpty()) {
                    return false; // 有合法移动，不是将死
                }
            }
        }

        return true; // 将死
    }
    //将死检测辅助方法——检查特定棋子有无可以摆脱将军的移动
    public List<Position> getLegalMoves(Piece piece) {
        List<Position> legalMoves = new ArrayList<>();

        if (piece.getPlayer() != currentPlayer) {
            return legalMoves;
        }

        // 使用高效方法获取可能移动
        List<Position> possibleMoves = piece.getPossibleMoves(allPieces);

        for (Position target : possibleMoves) {
            // 检查移动是否会导致被将军
            if (!willMoveCauseCheck(piece, target.getX(), target.getY())) {
                legalMoves.add(target);
            }
        }

        return legalMoves;
    }




    //困毙检测
    boolean isStalemate() {
        // 如果没有被将军
        if (isKingInCheck()) {
            return false;
        }

        // 检查当前玩家是否有任何合法的移动
        for (Piece piece : allPieces) {
            if (piece.getPlayer() == currentPlayer) {
                // 使用高效的getLegalMoves方法
                List<Position> legalMoves = getLegalMoves(piece);
                if (!legalMoves.isEmpty()) {
                    return false; // 有合法移动，不是将死
                }
            }
        }
        return true;
    }

}
