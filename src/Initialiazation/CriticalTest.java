package Initialiazation;

import java.util.List;

public class CriticalTest {
    public static void main(String[] args) {
        System.out.println("=== 完整棋子测试 ===\n");

        PieceFactory factory = new PieceFactory();
        List<Piece> pieces = factory.PiecesI();
        System.out.println("创建了 " + pieces.size() + " 个棋子\n");

        // 详细检查每个棋子
        System.out.println("详细检查：");
        int kingCount = 0, advisorCount = 0, elephantCount = 0;
        int horseCount = 0, rookCount = 0, cannonCount = 0, soldierCount = 0;
        int pieceCount = 0;

        for (Piece piece : pieces) {
            String className = piece.getClass().getSimpleName();
            Position pos = piece.getPosition();

            System.out.printf("  %s at (%d,%d) - 实际类: %s%n",
                    piece.getName(), pos.getX(), pos.getY(), className);

            // 使用instanceof检查
            if (piece instanceof King) kingCount++;
            else if (piece instanceof Advisor) advisorCount++;
            else if (piece instanceof Elephant) elephantCount++;
            else if (piece instanceof Horse) horseCount++;
            else if (piece instanceof Rook) rookCount++;
            else if (piece instanceof Cannon) cannonCount++;
            else if (piece instanceof Soldier) soldierCount++;
            else if (piece instanceof Piece) pieceCount++;
        }

        System.out.println("\n=== 统计结果 ===");
        System.out.println("King (帅/將): " + kingCount + " (应该是2)");
        System.out.println("Advisor (仕/士): " + advisorCount + " (应该是4)");
        System.out.println("Elephant (相/象): " + elephantCount + " (应该是4)");
        System.out.println("Horse (马/馬): " + horseCount + " (应该是4)");
        System.out.println("Rook (车/車): " + rookCount + " (应该是4)");
        System.out.println("Cannon (炮): " + cannonCount + " (应该是4)");
        System.out.println("Soldier (兵/卒): " + soldierCount + " (应该是10)");
        System.out.println("Piece基类: " + pieceCount + " (应该是0)");

        // 验证总数
        int total = kingCount + advisorCount + elephantCount + horseCount +
                rookCount + cannonCount + soldierCount + pieceCount;
        System.out.println("总数: " + total + " (应该是32)");
    }
}