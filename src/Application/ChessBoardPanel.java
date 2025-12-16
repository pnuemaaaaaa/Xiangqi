package Application;

import Initialiazation.ChessBoardModel;
import Initialiazation.Piece;
import Initialiazation.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardPanel extends JPanel {
    private final ChessBoardModel model;
    private JLabel turnLabel;
    private Piece selectedPiece = null;
    private JButton resetButton;

    /**
     * 单个棋盘格子的尺寸（px）
     */
    private static final int CELL_SIZE = 50;

    /**
     * 棋盘边界与窗口边界的边距
     */
    private static final int MARGIN = 30;

    /**
     * 棋子的半径
     */
    private static final int PIECE_RADIUS = 18;

    /**
     * 上方高度
     */
    private static final int UPPER_HEIGHT = 45;

    /**
     * 底部高度（用于放置按钮）
     */
    private static final int BOTTOM_HEIGHT = 35;

    public ChessBoardPanel(ChessBoardModel model) {
        this.model = model;
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(
                CELL_SIZE * (ChessBoardModel.getCols() - 1) + MARGIN * 2,
                CELL_SIZE * (ChessBoardModel.getRows() - 1) + MARGIN * 2 + UPPER_HEIGHT + BOTTOM_HEIGHT
        ));
        setBackground(new Color(220, 179, 92));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getY() < getHeight() - BOTTOM_HEIGHT) {
                    handleMouseClick(e.getX(), e.getY());
                }
            }
        });

        initResetButton();
    }

    /**
     * 初始化重置按钮
     */
    private void initResetButton() {
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("楷体", Font.PLAIN, 16));
        resetButton.setBackground(new Color(220, 179, 92));
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        resetButton.addActionListener(e -> showResetConfirmationDialog());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(220, 179, 92));
        bottomPanel.add(resetButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * 显示重置确认对话框
     */
    private void showResetConfirmationDialog() {
        Object[] options = {"Ensure", "Cancel"};
        int response = JOptionPane.showOptionDialog(
                this,
                "Do you want to reset the game?",
                "Reset Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (response == JOptionPane.YES_OPTION) {
            model.initializeChessBoard();
            selectedPiece = null;
            repaint();
            JOptionPane.showMessageDialog(this, "Game has been reset!", "Reset", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleMouseClick(int x, int y) {
        int posX = Math.round((float) (x - MARGIN) / CELL_SIZE);
        int posY = Math.round((float) (y - MARGIN - UPPER_HEIGHT) / CELL_SIZE);

        if (!model.isValidPosition(posX, posY)) {
            return;
        }

        if (selectedPiece == null) {
            selectedPiece = model.getPieceAt(posX, posY);
        } else {
            Piece targetPiece = model.getPieceAt(posX, posY);
            if (targetPiece == null) {
                model.movePiece(selectedPiece,targetPiece,posX,posY);
                selectedPiece = null;
            } else if (!targetPiece.getPlayer().equals(selectedPiece.getPlayer())) {
                model.movePiece(selectedPiece,targetPiece,posX,posY);
                selectedPiece = null;
            } else if (targetPiece.getPlayer().equals(selectedPiece.getPlayer())) {
                selectedPiece = targetPiece;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBoard(g2d);
        drawPieces(g2d);
        drawCurrentPlayer(g2d, model.getCurrentPlayer());
    }

    /**
     * 绘制棋盘
     */
    private void drawBoard(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1.8f));

        // 绘制横线
        g.drawLine(0, UPPER_HEIGHT, 2 * MARGIN + (ChessBoardModel.getCols() - 1) * CELL_SIZE, UPPER_HEIGHT);
        for (int i = 0; i < ChessBoardModel.getRows(); i++) {
            int y = MARGIN + i * CELL_SIZE;
            g.drawLine(MARGIN, y + UPPER_HEIGHT, MARGIN + (ChessBoardModel.getCols() - 1) * CELL_SIZE, y + UPPER_HEIGHT);
        }

        // 绘制竖线
        for (int i = 0; i < ChessBoardModel.getCols(); i++) {
            int x = MARGIN + i * CELL_SIZE;
            if (i == 0 || i == ChessBoardModel.getCols() - 1) {
                g.drawLine(x, MARGIN + UPPER_HEIGHT, x, MARGIN + (ChessBoardModel.getRows() - 1) * CELL_SIZE + UPPER_HEIGHT);
            } else {
                g.drawLine(x, MARGIN + UPPER_HEIGHT, x, MARGIN + 4 * CELL_SIZE + UPPER_HEIGHT);
                g.drawLine(x, MARGIN + 5 * CELL_SIZE + UPPER_HEIGHT, x, MARGIN + (ChessBoardModel.getRows() - 1) * CELL_SIZE + UPPER_HEIGHT);
            }
        }

        // 绘制“楚河”和“汉界”这两个文字
        g.setColor(Color.BLACK);
        g.setFont(new Font("楷体", Font.BOLD, 20));

        int riverY = MARGIN + 4 * CELL_SIZE + CELL_SIZE / 2;

        String chuHeText = "楚河";
        FontMetrics fm = g.getFontMetrics();
        int chuHeWidth = fm.stringWidth(chuHeText);
        g.drawString(chuHeText, MARGIN + CELL_SIZE * 2 - chuHeWidth / 2, riverY + 6 + UPPER_HEIGHT);

        String hanJieText = "汉界";
        int hanJieWidth = fm.stringWidth(hanJieText);
        g.drawString(hanJieText, MARGIN + CELL_SIZE * 6 - hanJieWidth / 2, riverY + 6 + UPPER_HEIGHT);
    }

    /**
     * 绘制棋子
     */
    private void drawPieces(Graphics2D g) {
        for (Piece piece : model.getAllPieces()) {
            int x = MARGIN + piece.getPosition().getX() * CELL_SIZE;
            int y = MARGIN + piece.getPosition().getY() * CELL_SIZE + UPPER_HEIGHT;

            boolean isSelected = (piece == selectedPiece);

            g.setColor(new Color(245, 222, 179));
            g.fillOval(x - PIECE_RADIUS, y - PIECE_RADIUS, PIECE_RADIUS * 2, PIECE_RADIUS * 2);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1.8f));
            g.drawOval(x - PIECE_RADIUS, y - PIECE_RADIUS, PIECE_RADIUS * 2, PIECE_RADIUS * 2);

            if (isSelected) {
                drawCornerBorders(g, x, y);
            }

            if (piece.getPlayer() == Player.RED) {
                g.setColor(new Color(200, 0, 0));
            } else {
                g.setColor(Color.BLACK);
            }
            g.setFont(new Font("楷体", Font.BOLD, 18));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(piece.getName());
            int textHeight = fm.getAscent();
            g.drawString(piece.getName(), x - textWidth / 2, y + textHeight / 2 - 2);
        }
    }

    /**
     * 绘制选中棋子时的蓝色外边框效果
     */
    private void drawCornerBorders(Graphics2D g, int centerX, int centerY) {
        g.setColor(new Color(0, 100, 255));
        g.setStroke(new BasicStroke(2.2f));

        int cornerSize = 22;
        int lineLength = 9;

        // 左上角的边框
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize + lineLength, centerY - cornerSize);
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize, centerY - cornerSize + lineLength);

        // 右上角的边框
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize - lineLength, centerY - cornerSize);
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize, centerY - cornerSize + lineLength);

        // 左下角的边框
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize + lineLength, centerY + cornerSize);
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize, centerY + cornerSize - lineLength);

        // 右下角的边框
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize - lineLength, centerY + cornerSize);
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize, centerY + cornerSize - lineLength);
    }

    /**
     * 绘制当前玩家
     */
    private void drawCurrentPlayer(Graphics2D g,Player currentPlayer) {
        if (currentPlayer == Player.BLACK) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(200, 0, 0));
        }
        g.setFont(new Font("楷体", Font.BOLD, 20));

        int middleY = UPPER_HEIGHT / 2 + 6;

        String text = "轮到" + currentPlayer.getDisplayName() + "走棋";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g.drawString(text, MARGIN + CELL_SIZE * 4 - textWidth / 2, middleY);
    }
}