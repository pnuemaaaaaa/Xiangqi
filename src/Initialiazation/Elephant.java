package Initialiazation;

public class Elephant extends Piece {

    public Elephant(Position position, Player player) {
        // 红方是"相"，黑方是"象"
        super(player == Player.RED ? "相" : "象",
                position,
                player);
    }
}
