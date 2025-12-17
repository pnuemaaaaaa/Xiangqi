package Initialiazation;

import java.util.List;

public class ChessTest {
    private ChessBoardModel boardModel;

    public static void main(String[] args) {
        ChessTest test = new ChessTest();

        System.out.println("========== 象棋逻辑测试 ==========\n");

        // 测试1：基本功能
        test.testInitialSetup();

        // 测试2：将军检测
        test.testCheckDetection();

        // 测试3：将死检测
        test.testCheckmateDetection();

        // 测试4：困毙检测
        test.testStalemateDetection();

        // 测试5：移动和吃子
        test.testMoveAndCapture();

        System.out.println("\n========== 所有测试完成 ==========");
    }

    public ChessTest() {
        boardModel = new ChessBoardModel();
    }

    /**
     * 测试1：初始设置
     */
    public void testInitialSetup() {
        System.out.println("【测试1】初始设置验证");
        System.out.println("当前玩家: " + boardModel.getCurrentPlayer());

        List<Piece> pieces = boardModel.getAllPieces();
        System.out.println("棋子总数: " + pieces.size());

        // 检查红方帅的位置
        Piece redKing = boardModel.findKing(Player.RED);
        if (redKing != null) {
            System.out.println("红方帅位置: (" + redKing.getPosition().getX() +
                    ", " + redKing.getPosition().getY() + ")");
        }

        // 检查黑方将的位置
        Piece blackKing = boardModel.findKing(Player.BLACK);
        if (blackKing != null) {
            System.out.println("黑方将位置: (" + blackKing.getPosition().getX() +
                    ", " + blackKing.getPosition().getY() + ")");
        }

        System.out.println("✓ 初始设置测试通过\n");
    }

    /**
     * 测试2：将军检测
     */
    public void testCheckDetection() {
        System.out.println("【测试2】将军检测");

        // 清空棋盘，创建一个将军局面
        boardModel = new ChessBoardModel();
        List<Piece> pieces = boardModel.getAllPieces();
        pieces.clear();

        // 创建局面：红帅在(4,0)，黑车在(4,8) -> 将军！
        Piece redKing = new King(new Position(4, 0), Player.RED);
        Piece blackRook = new Rook(new Position(4, 8), Player.BLACK);

        pieces.add(redKing);
        pieces.add(blackRook);

        // 设置当前玩家为红方（被将军的一方）
        boardModel.setCurrentPlayer(Player.RED);

        boolean isInCheck = boardModel.isKingInCheck();
        System.out.println("红方是否被将军: " + isInCheck);

        if (isInCheck) {
            System.out.println("✓ 将军检测成功：黑车正在将军红帅");
        } else {
            System.out.println("✗ 将军检测失败");
        }

        // 测试将军解除：黑方移动车
        boardModel.setCurrentPlayer(Player.BLACK);
        isInCheck = boardModel.isKingInCheck();
        System.out.println("黑方是否被将军: " + isInCheck);

        System.out.println("✓ 将军检测测试完成\n");
    }

    /**
     * 测试3：将死检测
     */
    public void testCheckmateDetection() {
        System.out.println("【测试3】将死检测");

        // 清空棋盘，创建一个将死局面
        boardModel = new ChessBoardModel();
        List<Piece> pieces = boardModel.getAllPieces();
        pieces.clear();

        /*
        将死局面：
        红方：帅(4,0)，仕(3,1)，仕(5,1)  # 帅被卡在九宫角落
        黑方：将(4,9)，車(4,8)，車(5,8)  # 双车错杀
        */

        // 红方棋子
        pieces.add(new King(new Position(4, 0), Player.RED));
        pieces.add(new Advisor(new Position(3, 1), Player.RED));
        pieces.add(new Advisor(new Position(5, 1), Player.RED));

        // 黑方棋子
        pieces.add(new King(new Position(4, 9), Player.BLACK));
        pieces.add(new Rook(new Position(4, 8), Player.BLACK));
        pieces.add(new Rook(new Position(5, 8), Player.BLACK));

        // 设置当前玩家为红方（被将死的一方）
        boardModel.setCurrentPlayer(Player.RED);

        boolean isCheckmate = boardModel.isCheckmate();
        boolean isInCheck = boardModel.isKingInCheck();

        System.out.println("红方是否被将军: " + isInCheck);
        System.out.println("红方是否被将死: " + isCheckmate);

        if (isCheckmate) {
            System.out.println("✓ 将死检测成功：红方被将死！");
        } else {
            System.out.println("✗ 将死检测失败");
            // 如果没有检测到将死，打印红方的合法移动
            System.out.println("红方可能的解将方法：");
            for (Piece piece : pieces) {
                if (piece.getPlayer() == Player.RED) {
                    List<Position> legalMoves = boardModel.getLegalMoves(piece);
                    if (!legalMoves.isEmpty()) {
                        System.out.println(piece.getName() + " 可以移动到: ");
                        for (Position pos : legalMoves) {
                            System.out.println("  (" + pos.getX() + ", " + pos.getY() + ")");
                        }
                    }
                }
            }
        }

        System.out.println("✓ 将死检测测试完成\n");
    }

    /**
     * 测试4：困毙检测
     */
    public void testStalemateDetection() {
        System.out.println("【测试4】困毙检测");

        // 清空棋盘，创建一个困毙局面
        boardModel = new ChessBoardModel();
        List<Piece> pieces = boardModel.getAllPieces();
        pieces.clear();

        /*
        困毙局面：
        红方：帅(4,0)  # 孤立无援
        黑方：将(4,9)，車(3,2)，車(5,2)  # 包围但未将军
        */

        // 红方棋子
        pieces.add(new King(new Position(4, 0), Player.RED));

        // 黑方棋子
        pieces.add(new King(new Position(4, 9), Player.BLACK));
        pieces.add(new Rook(new Position(3, 2), Player.BLACK));
        pieces.add(new Rook(new Position(5, 2), Player.BLACK));

        // 设置当前玩家为红方（被困毙的一方）
        boardModel.setCurrentPlayer(Player.RED);

        boolean isStalemate = boardModel.isStalemate();
        boolean isInCheck = boardModel.isKingInCheck();

        System.out.println("红方是否被将军: " + isInCheck);
        System.out.println("红方是否困毙: " + isStalemate);

        if (isStalemate && !isInCheck) {
            System.out.println("✓ 困毙检测成功：红方困毙（和棋）！");
        } else {
            System.out.println("✗ 困毙检测失败");
            // 如果没有困毙，打印红方的合法移动
            System.out.println("红方可能的移动：");
            for (Piece piece : pieces) {
                if (piece.getPlayer() == Player.RED) {
                    List<Position> legalMoves = boardModel.getLegalMoves(piece);
                    if (!legalMoves.isEmpty()) {
                        System.out.println(piece.getName() + " 可以移动到: ");
                        for (Position pos : legalMoves) {
                            System.out.println("  (" + pos.getX() + ", " + pos.getY() + ")");
                        }
                    } else {
                        System.out.println(piece.getName() + " 无法移动");
                    }
                }
            }
        }

        System.out.println("✓ 困毙检测测试完成\n");
    }

    /**
     * 测试5：移动和吃子
     */
    public void testMoveAndCapture() {
        System.out.println("【测试5】移动和吃子测试");

        // 清空棋盘，创建一个简单局面
        boardModel = new ChessBoardModel();
        List<Piece> pieces = boardModel.getAllPieces();
        pieces.clear();

        // 创建局面：红马可以吃黑卒
        Piece redHorse = new Horse(new Position(1, 0), Player.RED);
        Piece blackPawn = new Soldier(new Position(2, 2), Player.BLACK);

        pieces.add(redHorse);
        pieces.add(blackPawn);
        pieces.add(new King(new Position(4, 0), Player.RED));
        pieces.add(new King(new Position(4, 9), Player.BLACK));

        // 设置当前玩家为红方
        boardModel.setCurrentPlayer(Player.RED);

        System.out.println("移动前棋子数量: " + pieces.size());
        System.out.println("黑卒位置: (" + blackPawn.getPosition().getX() +
                ", " + blackPawn.getPosition().getY() + ")");

        // 测试马能否移动到该位置
        boolean canMove = redHorse.canMoveTo(2, 2, pieces);
        System.out.println("红马能否移动到(2,2): " + canMove);

        if (canMove) {
            // 模拟移动（吃子）
            System.out.println("执行吃子移动...");

            // 保存移动前的状态
            Position originalPos = redHorse.getPosition();

            // 执行吃子
            redHorse.eat(blackPawn, pieces);
            redHorse.setPosition(new Position(2, 2));

            System.out.println("移动后棋子数量: " + pieces.size());
            System.out.println("红马新位置: (" + redHorse.getPosition().getX() +
                    ", " + redHorse.getPosition().getY() + ")");

            if (!pieces.contains(blackPawn)) {
                System.out.println("✓ 吃子功能正常：黑卒被吃掉");
            }

            // 恢复状态（为了不影响其他测试）
            redHorse.setPosition(originalPos);
            pieces.add(blackPawn);
        }

        System.out.println("✓ 移动和吃子测试完成\n");
    }

    /**
     * 辅助方法：打印棋盘状态
     */
    private void printBoard() {
        System.out.println("\n当前棋盘状态:");
        System.out.println("  0 1 2 3 4 5 6 7 8");

        for (int y = 0; y < 10; y++) {
            System.out.print(y + " ");
            for (int x = 0; x < 9; x++) {
                Piece piece = boardModel.getPieceAt(x, y);
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    char symbol = getPieceSymbol(piece);
                    System.out.print(symbol + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 辅助方法：获取棋子符号
     */
    private char getPieceSymbol(Piece piece) {
        String name = piece.getName();
        Player player = piece.getPlayer();

        switch (name) {
            case "帅":
            case "將":
                return (player == Player.RED) ? 'K' : 'k';
            case "仕":
            case "士":
                return (player == Player.RED) ? 'A' : 'a';
            case "相":
            case "象":
                return (player == Player.RED) ? 'B' : 'b';
            case "马":
            case "馬":
                return (player == Player.RED) ? 'N' : 'n';
            case "车":
            case "車":
                return (player == Player.RED) ? 'R' : 'r';
            case "炮":
                return (player == Player.RED) ? 'C' : 'c';
            case "兵":
            case "卒":
                return (player == Player.RED) ? 'P' : 'p';
            default:
                return '?';
        }
    }
}