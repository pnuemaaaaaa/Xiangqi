package Application;

import User.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog {
    private UserManager userManager;
    private boolean registered = false;
    private String registeredUsername = "";

    // 界面组件
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel statusLabel;

    public RegisterDialog(JFrame parent, UserManager userManager) {
        super(parent, "用户注册", true);
        this.userManager = userManager;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题标签
        JLabel titleLabel = new JLabel("注册新账户", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // 用户名
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        inputPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        inputPanel.add(usernameField, gbc);

        // 密码
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        inputPanel.add(passwordField, gbc);

        // 确认密码
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel confirmLabel = new JLabel("确认密码:");
        confirmLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        inputPanel.add(confirmLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        confirmPasswordField = new JPasswordField(15);
        inputPanel.add(confirmPasswordField, gbc);

        // 密码要求提示
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JLabel passwordHint = new JLabel("提示: 用户名和密码不能包含空格");
        passwordHint.setFont(new Font("宋体", Font.ITALIC, 12));
        passwordHint.setForeground(Color.GRAY);
        inputPanel.add(passwordHint, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("宋体", Font.BOLD, 14));
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.BLACK);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });

        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // 状态标签
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);

        // 添加所有组件到主面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 添加到对话框
        add(mainPanel);

        // 设置回车键触发注册
        getRootPane().setDefaultButton(registerButton);
    }

    /**
     * 处理注册按钮点击
     */
    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // 验证输入
        if (username.isEmpty() || password.isEmpty()) {
            setStatus("用户名和密码不能为空", Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            setStatus("两次输入的密码不一致", Color.RED);
            confirmPasswordField.setText("");
            confirmPasswordField.requestFocus();
            return;
        }

        // 检查是否包含空格（UserManager会处理，但这里给用户提示）
        if (username.contains(" ") || password.contains(" ")) {
            setStatus("用户名和密码不能包含空格", Color.ORANGE);
        }

        // 尝试注册
        boolean success = userManager.register(username, password);
        if (success) {
            registered = true;
            registeredUsername = usernameField.getText().replaceAll("\\s+", "");
            setStatus("注册成功！即将进入游戏", new Color(0, 150, 0));

            // 延迟1秒后关闭对话框
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            setStatus("注册失败，用户名可能已存在", Color.RED);
        }
    }

    /**
     * 设置状态消息
     */
    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    /**
     * 是否注册成功
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * 获取注册的用户名
     */
    public String getRegisteredUsername() {
        return registeredUsername;
    }
}