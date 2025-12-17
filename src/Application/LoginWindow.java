package Application;

import User.UserManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private UserManager userManager;

    public LoginWindow() {
        // 初始化用户管理器
        userManager = new UserManager();
        userManager.addTestUsers(); // 添加测试用户

        // 设置窗口属性
        setTitle("中国象棋 - 选择登录方式");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // 和LoginDialog、RegisterDialog一样大小
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 添加组件
        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 标题标签
        JLabel titleLabel = new JLabel("欢迎来到中国象棋", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 按钮面板 - 使用GridLayout垂直排列三个按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 15, 25));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // 创建按钮样式
        Font buttonFont = new Font("宋体", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 60);

        // 登录按钮
        JButton loginButton = new JButton("用户登录");
        styleButton(loginButton, buttonFont, buttonSize, new Color(70, 130, 180));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginDialog();
            }
        });

        // 注册按钮
        JButton registerButton = new JButton("用户注册");
        styleButton(registerButton, buttonFont, buttonSize, new Color(60, 179, 113));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterDialog();
            }
        });

        // 游客登录按钮
        JButton guestButton = new JButton("游客登录");
        styleButton(guestButton, buttonFont, buttonSize, new Color(218, 165, 32));
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuestLogin();
            }
        });

        // 添加按钮到面板
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(guestButton);

        // 添加按钮面板到主面板
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // 底部提示标签
        JLabel hintLabel = new JLabel("请选择登录方式", SwingConstants.CENTER);
        hintLabel.setFont(new Font("宋体", Font.ITALIC, 12));
        hintLabel.setForeground(Color.GRAY);
        mainPanel.add(hintLabel, BorderLayout.SOUTH);

        // 添加到窗口
        add(mainPanel);
    }

    /**
     * 统一按钮样式
     */
    private void styleButton(JButton button, Font font, Dimension size, Color bgColor) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        // 鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    /**
     * 打开登录对话框
     */
    private void openLoginDialog() {
        LoginDialog loginDialog = new LoginDialog(this, userManager);
        loginDialog.setVisible(true);

        // 登录成功后启动游戏
        if (loginDialog.isLogined()) {
            startGame();
        }
    }

    /**
     * 打开注册对话框
     */
    private void openRegisterDialog() {
        RegisterDialog registerDialog = new RegisterDialog(this, userManager);
        registerDialog.setVisible(true);

        // 注册成功后启动游戏
        if (registerDialog.isRegistered()) {
            startGame();
        }
    }

    /**
     * 处理游客登录
     */
    private void handleGuestLogin() {
        userManager.loginAsGuest();
        startGame();
    }

    /**
     * 启动游戏主窗口
     */
    private void startGame() {
        // 关闭登录窗口
        dispose();

        // 启动游戏
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("中国象棋");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Initialiazation.ChessBoardModel model = new Initialiazation.ChessBoardModel();
                ChessBoardPanel boardPanel = new ChessBoardPanel(model);

                frame.add(boardPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}