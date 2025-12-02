package Initialiazation;

public class Cannon extends Piece {

    public Cannon(Position position, Player player) {
        // 炮不分红黑名称，都是"炮"
        super("炮", position, player);
    }
}
