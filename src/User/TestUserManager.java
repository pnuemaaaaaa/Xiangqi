package User;

public class TestUserManager {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        System.out.println("==========================================");
        System.out.println("       中国象棋用户管理系统测试");
        System.out.println("==========================================\n");

        // 添加测试用户
        userManager.addTestUsers();

        // 测试1: 正常登录
        System.out.println("测试1: 正常登录测试");
        System.out.println("------------------------");
        boolean login1 = userManager.login("test1", "123456");
        System.out.println("登录结果: " + (login1 ? "✓ 成功" : "✗ 失败"));
        printCurrentUser(userManager);
        System.out.println();

        // 测试2: 登出
        System.out.println("测试2: 登出测试");
        System.out.println("------------------------");
        userManager.logout();
        printCurrentUser(userManager);
        System.out.println();

        // 测试3: 错误密码登录
        System.out.println("测试3: 错误密码登录");
        System.out.println("------------------------");
        boolean login2 = userManager.login("test1", "wrongpassword");
        System.out.println("登录结果: " + (login2 ? "✓ 成功" : "✗ 失败"));
        System.out.println();

        // 测试4: 不存在的用户登录
        System.out.println("测试4: 不存在的用户登录");
        System.out.println("------------------------");
        boolean login3 = userManager.login("nonexist", "password");
        System.out.println("登录结果: " + (login3 ? "✓ 成功" : "✗ 失败"));
        System.out.println();

        // 测试5: 游客登录
        System.out.println("测试5: 游客登录");
        System.out.println("------------------------");
        userManager.loginAsGuest();
        printCurrentUser(userManager);
        System.out.println();

        // 测试6: 游客状态下注册新用户
        System.out.println("测试6: 游客状态下注册新用户");
        System.out.println("------------------------");
        boolean register1 = userManager.register("player1", "pass123");
        System.out.println("注册结果: " + (register1 ? "✓ 成功" : "✗ 失败"));
        printCurrentUser(userManager);
        System.out.println();

        // 测试7: 重复注册
        System.out.println("测试7: 重复注册测试");
        System.out.println("------------------------");
        userManager.logout();
        boolean register2 = userManager.register("player1", "newpass");
        System.out.println("注册结果: " + (register2 ? "✓ 成功" : "✗ 失败"));
        System.out.println();

        // 测试8: 注册带空格的用户名
        System.out.println("测试8: 带空格的用户名注册");
        System.out.println("------------------------");
        boolean register3 = userManager.register("  john doe  ", "my password ");
        System.out.println("注册结果: " + (register3 ? "✓ 成功" : "✗ 失败"));
        if (register3) {
            printCurrentUser(userManager);
        }
        System.out.println();

        // 测试9: 空用户名或密码注册
        System.out.println("测试9: 空用户名或密码注册");
        System.out.println("------------------------");
        boolean register4 = userManager.register("", "password");
        System.out.println("空用户名注册: " + (register4 ? "✓ 成功" : "✗ 失败"));

        boolean register5 = userManager.register("testuser", "");
        System.out.println("空密码注册: " + (register5 ? "✓ 成功" : "✗ 失败"));

        boolean register6 = userManager.register("   ", "password");
        System.out.println("纯空格用户名注册: " + (register6 ? "✓ 成功" : "✗ 失败"));
        System.out.println();

        // 测试10: 已登录状态下再登录
        System.out.println("测试10: 已登录状态下尝试登录其他用户");
        System.out.println("------------------------");
        userManager.logout();
        userManager.login("test2", "abcdef");
        boolean login4 = userManager.login("test1", "123456");
        System.out.println("已登录时尝试登录其他用户: " + (login4 ? "✓ 成功" : "✗ 失败"));
        printCurrentUser(userManager);
        System.out.println();

        // 测试11: 显示所有用户
        System.out.println("测试11: 显示所有注册用户");
        System.out.println("------------------------");
        displayAllUsers(userManager);

        System.out.println("\n==========================================");
        System.out.println("            测试完成");
        System.out.println("==========================================");
    }

    /**
     * 打印当前用户信息
     */
    private static void printCurrentUser(UserManager userManager) {
        User current = userManager.getCurrentUser();
        if (current != null) {
            System.out.println("当前用户: " + current.getUsername());
            System.out.println("用户类型: " + (current.isGuest() ? "游客" : "注册用户"));
            System.out.println("注册时间: " + current.getRegisterDate());
            System.out.println("最后登录: " + current.getLastLogin());
        } else {
            System.out.println("当前没有用户登录");
        }
    }

    /**
     * 显示所有注册用户
     */
    private static void displayAllUsers(UserManager userManager) {
        System.out.println("已注册用户列表:");
        System.out.println("用户名\t\t\t注册时间");
        System.out.println("----------------------------------------");

        for (User user : userManager.getAllUsers()) {
            if (!user.isGuest()) {
                System.out.printf("%-20s\t%s%n",
                        user.getUsername(),
                        user.getRegisterDate());
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("总用户数: " + userManager.getAllUsers().size());
    }
}