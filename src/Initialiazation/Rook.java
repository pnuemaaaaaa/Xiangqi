package Initialiazation;

public class Rook extends Piece {

    public Rook(Position position, Player player) {
        // 红方是"车"，黑方是"車"（写法不同）
        super(player == Player.RED ? "车" : "車",
                position,
                player);
    }
}
