package Initialiazation;

public class Horse extends Piece {
    public Horse(Position position, Player player) {
        super(player == Player.RED ? "马" : "馬", position, player);
    }
}