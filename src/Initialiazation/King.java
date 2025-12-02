package Initialiazation;

public class King extends Piece {

    public King(Position position, Player player) {
        // 根据玩家决定名称：红方是"帅"，黑方是"将"
        super(player == Player.RED ? "帅" : "將",
                position,
                player);
    }
}
