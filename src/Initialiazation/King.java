package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Position position, Player player) {
        // 根据玩家决定名称：红方是"帅"，黑方是"将"
        super(player == Player.RED ? "帅" : "將",
                position,
                player);
    }

    @Override
    public boolean canMoveTo(int newX, int newY, List<Piece> allPieces) {
        int currentX = this.getPosition().getX();
        int currentY = this.getPosition().getY();
        // 1.2.基本检查
        if (!isWithinBoard(newX, newY) || isSamePosition(newX, newY)) return false;
        if (getPieceAt(newX,newY,allPieces) != null &&
                getPieceAt(newX,newY,allPieces).getPlayer() == this.getPlayer() ) return false;

        // 3. 检查是否在九宫格内
        if (!isInPalace(newX, newY)) return false;

        // 5. 检查移动距离：只能走一步（上下左右）
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
    private boolean isInPalace(int x, int y) {
        // 红方九宫：x:3-5, y:0-2
        // 黑方九宫：x:3-5, y:7-9
        if (getPlayer() == Player.RED) {
            return x >= 3 && x <= 5 && y >= 0 && y <= 2;
        } else {
            return x >= 3 && x <= 5 && y >= 7 && y <= 9;
        }
    }
    private Piece findEnemyKing(Player enemyPlayer, List<Piece> allPieces) {
        for (Piece piece : allPieces) {
            String name = piece.getName();
            if ((enemyPlayer == Player.RED && name.equals("帅")) ||
                    (enemyPlayer == Player.BLACK && name.equals("将"))) {
                return piece;
            }
        }
        return null;
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
    public List<Position> getPossibleMoves(List<Piece> allPieces){
        List<Position> moves = new ArrayList<>();
        if(getPlayer() == Player.RED) {
            for (int x = 3; x < 6; x++) {
                for (int y = 0; y < 3; y++) {
                    if(canMoveTo(x,y,allPieces)){
                        moves.add(new Position(x,y));
                    }
                }
            }
        }else{
            for (int x = 3; x < 6; x++) {
                for (int y = 7; y < 10; y++) {
                    if(canMoveTo(x,y,allPieces)){
                        moves.add(new Position(x,y));
                    }
                }
            }
        }
        return moves;
    }


}
