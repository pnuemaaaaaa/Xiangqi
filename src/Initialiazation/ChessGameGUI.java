package Initialiazation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChessGameGUI extends JFrame {
    private JPanel chessBoard;
    private Piece[][] boardPieces = new Piece[10][9];
    private PieceFactory factory;
    private JLabel statusLabel;
    private JButton restartButton;
    private JLabel redCapturedLabel;
    private JLabel blackCapturedLabel;
    private java.util.List<Piece> redCaptured = new java.util.ArrayList<>();
    private java.util.List<Piece> blackCaptured = new java.util.ArrayList<>();

    // 棋子颜色
    private static final Color RED_PIECE_COLOR = new Color(200, 50, 50);
    private static final Color BLACK_PIECE_COLOR = new Color(30, 30, 30);
    private static final Color BOARD_COLOR = new Color(238, 210, 161);
    private static final Color LINE_COLOR = new Color(120, 80, 40);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 200, 150);

    private int selectedX = -1, selectedY = -1;
    private boolean isRedTurn = true; // 红方先行

    public ChessGameGUI() {
        super("中国象棋");
        factory = new PieceFactory();
        initializeUI();
        initializeBoard();
        setVisible(true);
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 40));

        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40, 40, 55));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel("中国象棋");
        titleLabel.setFont(new Font("楷体", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // 主游戏区域
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setBackground(new Color(30, 30, 40));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 左侧 - 红方信息
        JPanel redPanel = createPlayerPanel("红方", RED_PIECE_COLOR);

        // 中间 - 棋盘
        chessBoard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
                drawPieces(g);
                if (selectedX != -1 && selectedY != -1) {
                    drawSelection(g);
                }
            }
        };

        chessBoard.setPreferredSize(new Dimension(700, 780));
        chessBoard.setBackground(BOARD_COLOR);
        chessBoard.setBorder(BorderFactory.createLineBorder(new Color(100, 70, 30), 4));

        // 添加鼠标监听器用于棋子移动
        chessBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleBoardClick(e.getX(), e.getY());
            }
        });

        // 右侧 - 黑方信息
        JPanel blackPanel = createPlayerPanel("黑方", BLACK_PIECE_COLOR);

        mainPanel.add(redPanel, BorderLayout.WEST);
        mainPanel.add(chessBoard, BorderLayout.CENTER);
        mainPanel.add(blackPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // 底部控制面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(40, 40, 55));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // 状态标签
        statusLabel = new JLabel("红方先行");
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);

        // 重新开始按钮
        restartButton = new JButton("重新开始");
        styleButton(restartButton);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // 退出按钮
        JButton exitButton = new JButton("退出游戏");
        styleButton(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(ChessGameGUI.this,
                        "确定要退出游戏吗？", "退出确认",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // 设置窗口大小并居中
        setSize(1100, 950);
        setLocationRelativeTo(null);

        setLocationRelativeTo(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2 - 20; // 上移20像素，避开任务栏
        setLocation(x, Math.max(0, y)); // 确保y不小于0

        setResizable(false);
    }

    private JPanel createPlayerPanel(String playerName, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(180, 780));
        panel.setBackground(new Color(40, 40, 55));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 90), 2),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        // 玩家名称
        JLabel nameLabel = new JLabel(playerName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("楷体", Font.BOLD, 28));
        nameLabel.setForeground(color);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(nameLabel, BorderLayout.NORTH);

        // 被吃掉的棋子区域
        JPanel capturedPanel = new JPanel();
        capturedPanel.setLayout(new BoxLayout(capturedPanel, BoxLayout.Y_AXIS));
        capturedPanel.setBackground(new Color(50, 50, 65));
        capturedPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 120), 1),
                "被吃棋子",
                0, 0,
                new Font("微软雅黑", Font.PLAIN, 14),
                new Color(180, 180, 200)
        ));

        JLabel capturedLabel;
        if (playerName.equals("红方")) {
            redCapturedLabel = new JLabel("暂无");
            redCapturedLabel.setFont(new Font("楷体", Font.PLAIN, 20));
            redCapturedLabel.setForeground(RED_PIECE_COLOR);
            capturedLabel = redCapturedLabel;
        } else {
            blackCapturedLabel = new JLabel("暂无");
            blackCapturedLabel.setFont(new Font("楷体", Font.PLAIN, 20));
            blackCapturedLabel.setForeground(BLACK_PIECE_COLOR);
            capturedLabel = blackCapturedLabel;
        }

        capturedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        capturedPanel.add(Box.createVerticalGlue());
        capturedPanel.add(capturedLabel);
        capturedPanel.add(Box.createVerticalGlue());

        panel.add(capturedPanel, BorderLayout.CENTER);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 160, 210), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(90, 150, 200));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void initializeBoard() {
        // 清空棋盘
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                boardPieces[i][j] = null;
            }
        }

        // 从工厂获取棋子并放置到棋盘
        List<Piece> pieces = factory.PiecesI();
        for (Piece piece : pieces) {
            Position pos = piece.getPosition();
            if (pos != null) {
                boardPieces[pos.getY()][pos.getX()] = piece;
            }
        }

        // 清空被吃掉的棋子
        redCaptured.clear();
        blackCaptured.clear();
        updateCapturedDisplay();

        // 重置游戏状态
        selectedX = -1;
        selectedY = -1;
        isRedTurn = true;
        statusLabel.setText("红方先行");
        statusLabel.setForeground(RED_PIECE_COLOR);

        chessBoard.repaint();
    }

    private void drawBoard(Graphics g) {
        int width = chessBoard.getWidth();
        int height = chessBoard.getHeight();

        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, width, height);

        g.setColor(LINE_COLOR);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2.0f));

        // 计算格子大小
        int cellWidth = width / 9;
        int cellHeight = height / 10;

        // 绘制横线
        for (int i = 0; i <= 9; i++) {
            int y = i * cellHeight;
            g2d.drawLine(cellWidth, y, width - cellWidth, y);
        }

        // 绘制竖线
        for (int i = 0; i <= 8; i++) {
            int x = i * cellWidth;
            // 楚河汉界部分不画竖线
            if (i == 0 || i == 8) {
                g2d.drawLine(x, cellHeight, x, height - cellHeight);
            } else {
                g2d.drawLine(x, 0, x, height);
            }
        }

        // 绘制九宫斜线
        g2d.drawLine(3 * cellWidth, 0, 5 * cellWidth, 2 * cellHeight);
        g2d.drawLine(5 * cellWidth, 0, 3 * cellWidth, 2 * cellHeight);
        g2d.drawLine(3 * cellWidth, 7 * cellHeight, 5 * cellWidth, 9 * cellHeight);
        g2d.drawLine(5 * cellWidth, 7 * cellHeight, 3 * cellWidth, 9 * cellHeight);

        // 绘制楚河汉界文字
        g2d.setFont(new Font("楷体", Font.BOLD, 40));
        g2d.setColor(new Color(160, 50, 50));

        // 调整文字位置，使其在中间行显示
        FontMetrics fm = g2d.getFontMetrics();
        String riverText = "楚河汉界";
        int textWidth = fm.stringWidth(riverText);
        int textX = (width - textWidth) / 2;
        int textY = 5 * cellHeight + fm.getAscent() / 2;

        g2d.drawString(riverText, textX, textY);

        // 绘制坐标标记
        g2d.setFont(new Font("楷体", Font.PLAIN, 16));
        g2d.setColor(new Color(100, 70, 40));

        // 横坐标（数字）
        for (int i = 0; i < 9; i++) {
            String num = String.valueOf(i + 1);
            int numWidth = g2d.getFontMetrics().stringWidth(num);
            int numX = i * cellWidth + (cellWidth - numWidth) / 2;
            g2d.drawString(num, numX, height - 5);
        }

        // 纵坐标（汉字）
        String[] verticalLabels = {"九", "八", "七", "六", "五", "四", "三", "二", "一", "十"};
        for (int i = 0; i < 10; i++) {
            String label = verticalLabels[i];
            int labelWidth = g2d.getFontMetrics().stringWidth(label);
            int labelX = width - labelWidth - 5;
            int labelY = i * cellHeight + (cellHeight + g2d.getFontMetrics().getAscent()) / 2;
            g2d.drawString(label, labelX, labelY);
        }
    }

    private void drawPieces(Graphics g) {
        int width = chessBoard.getWidth();
        int height = chessBoard.getHeight();
        int cellWidth = width / 9;
        int cellHeight = height / 10;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                Piece piece = boardPieces[y][x];
                if (piece != null) {
                    int centerX = x * cellWidth + cellWidth / 2;
                    int centerY = y * cellHeight + cellHeight / 2;
                    int pieceRadius = Math.min(cellWidth, cellHeight) / 2 - 5;

                    // 绘制棋子背景
                    if (piece.getPlayer() == Player.RED) {
                        g2d.setColor(RED_PIECE_COLOR);
                    } else {
                        g2d.setColor(BLACK_PIECE_COLOR);
                    }
                    g2d.fillOval(centerX - pieceRadius, centerY - pieceRadius,
                            pieceRadius * 2, pieceRadius * 2);

                    // 绘制棋子边框
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(2.0f));
                    g2d.drawOval(centerX - pieceRadius, centerY - pieceRadius,
                            pieceRadius * 2, pieceRadius * 2);

                    // 绘制棋子文字
                    g2d.setColor(Color.WHITE);
                    Font pieceFont = new Font("楷体", Font.BOLD, pieceRadius);
                    g2d.setFont(pieceFont);

                    FontMetrics fm = g2d.getFontMetrics();
                    String pieceName = piece.getName();
                    int textWidth = fm.stringWidth(pieceName);
                    int textHeight = fm.getAscent();

                    g2d.drawString(pieceName,
                            centerX - textWidth / 2,
                            centerY + textHeight / 2 - 5);
                }
            }
        }
    }

    private void drawSelection(Graphics g) {
        int width = chessBoard.getWidth();
        int height = chessBoard.getHeight();
        int cellWidth = width / 9;
        int cellHeight = height / 10;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(HIGHLIGHT_COLOR);
        g2d.fillRect(selectedX * cellWidth, selectedY * cellHeight,
                cellWidth, cellHeight);

        // 绘制选中边框
        g2d.setColor(new Color(255, 100, 100));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawRect(selectedX * cellWidth, selectedY * cellHeight,
                cellWidth, cellHeight);
    }

    private void handleBoardClick(int clickX, int clickY) {
        int width = chessBoard.getWidth();
        int height = chessBoard.getHeight();
        int cellWidth = width / 9;
        int cellHeight = height / 10;

        int x = clickX / cellWidth;
        int y = clickY / cellHeight;

        // 确保在棋盘范围内
        if (x < 0 || x >= 9 || y < 0 || y >= 10) {
            return;
        }

        Piece clickedPiece = boardPieces[y][x];

        // 如果之前没有选中棋子，现在选中一个棋子
        if (selectedX == -1 || selectedY == -1) {
            if (clickedPiece != null) {
                // 检查是否轮到该玩家
                if ((isRedTurn && clickedPiece.getPlayer() == Player.RED) ||
                        (!isRedTurn && clickedPiece.getPlayer() == Player.BLACK)) {
                    selectedX = x;
                    selectedY = y;
                    chessBoard.repaint();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "现在轮到" + (isRedTurn ? "红方" : "黑方") + "走棋！",
                            "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        // 如果之前已经选中了棋子，现在尝试移动
        else {
            Piece selectedPiece = boardPieces[selectedY][selectedX];

            // 如果是同一个位置，取消选择
            if (selectedX == x && selectedY == y) {
                selectedX = -1;
                selectedY = -1;
                chessBoard.repaint();
                return;
            }

            // 尝试移动棋子
            if (isValidMove(selectedPiece, x, y)) {
                // 如果目标位置有棋子，吃掉它
                if (clickedPiece != null) {
                    capturePiece(clickedPiece);
                }

                // 移动棋子
                boardPieces[y][x] = selectedPiece;
                boardPieces[selectedY][selectedX] = null;

                // 更新棋子位置
                selectedPiece.setPosition(new Position(x, y));

                // 切换回合
                isRedTurn = !isRedTurn;
                statusLabel.setText(isRedTurn ? "红方走棋" : "黑方走棋");
                statusLabel.setForeground(isRedTurn ? RED_PIECE_COLOR : BLACK_PIECE_COLOR);

                // 检查游戏是否结束
                checkGameEnd();
            } else {
                JOptionPane.showMessageDialog(this,
                        "该棋子不能移动到此位置！",
                        "无效移动", JOptionPane.WARNING_MESSAGE);
            }

            // 清除选择
            selectedX = -1;
            selectedY = -1;
            chessBoard.repaint();
        }
    }

    private boolean isValidMove(Piece piece, int targetX, int targetY) {
        // 这里需要根据象棋规则实现具体的移动验证
        // 由于您的后端代码中尚未实现移动规则，这里先允许所有移动
        // 在实际游戏中，您需要根据棋子类型（车、马、炮等）实现具体的移动规则

        // 简单验证：目标位置不能有自己的棋子
        Piece targetPiece = boardPieces[targetY][targetX];
        if (targetPiece != null && targetPiece.getPlayer() == piece.getPlayer()) {
            return false;
        }

        return true; // 临时允许所有移动
    }

    private void capturePiece(Piece piece) {
        if (piece.getPlayer() == Player.RED) {
            blackCaptured.add(piece);
        } else {
            redCaptured.add(piece);
        }
        updateCapturedDisplay();
    }

    private void updateCapturedDisplay() {
        StringBuilder redText = new StringBuilder("<html><center>");
        if (redCaptured.isEmpty()) {
            redText.append("暂无");
        } else {
            for (int i = 0; i < redCaptured.size(); i++) {
                if (i > 0 && i % 4 == 0) redText.append("<br>");
                redText.append(redCaptured.get(i).getName()).append(" ");
            }
        }
        redText.append("</center></html>");
        redCapturedLabel.setText(redText.toString());

        StringBuilder blackText = new StringBuilder("<html><center>");
        if (blackCaptured.isEmpty()) {
            blackText.append("暂无");
        } else {
            for (int i = 0; i < blackCaptured.size(); i++) {
                if (i > 0 && i % 4 == 0) blackText.append("<br>");
                blackText.append(blackCaptured.get(i).getName()).append(" ");
            }
        }
        blackText.append("</center></html>");
        blackCapturedLabel.setText(blackText.toString());
    }

    private void checkGameEnd() {
        // 检查是否有一方的将/帅被吃掉
        boolean redKingAlive = false;
        boolean blackKingAlive = false;

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 9; x++) {
                Piece piece = boardPieces[y][x];
                if (piece != null && piece instanceof King) {
                    if (piece.getPlayer() == Player.RED) {
                        redKingAlive = true;
                    } else {
                        blackKingAlive = true;
                    }
                }
            }
        }

        if (!redKingAlive) {
            JOptionPane.showMessageDialog(this,
                    "黑方获胜！红方的帅被吃掉了。",
                    "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        } else if (!blackKingAlive) {
            JOptionPane.showMessageDialog(this,
                    "红方获胜！黑方的将被吃掉了。",
                    "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        }
    }

    private void restartGame() {
        int result = JOptionPane.showConfirmDialog(this,
                "确定要重新开始游戏吗？", "重新开始",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            initializeBoard();
        }
    }

    public static void main(String[] args) {
        // 使用SwingUtilities确保线程安全
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // 设置系统外观
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new ChessGameGUI();
            }
        });
    }
}