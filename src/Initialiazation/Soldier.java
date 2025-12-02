package Initialiazation;

public class Soldier extends Piece {

    public Soldier(Position position, Player player) {
        // 红方是"兵"，黑方是"卒"
        super(player == Player.RED ? "兵" : "卒",
                position,
                player);
    }
}
