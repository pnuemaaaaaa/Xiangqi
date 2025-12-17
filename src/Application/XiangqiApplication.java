package Application;

import javax.swing.*;

public class XiangqiApplication {
    public static void main(String[] args) {
        // 设置Swing外观为系统默认
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 启动登录窗口（而不是直接启动游戏）
        SwingUtilities.invokeLater(() -> {
            new LoginWindow();
        });
    }
}