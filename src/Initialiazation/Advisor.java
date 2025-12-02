package Initialiazation;

public class Advisor extends Piece {

    public Advisor(Position position, Player player) {
        // 红方是"仕"，黑方是"士"
        super(player == Player.RED ? "仕" : "士",
                position,
                player);
    }
}