package User;

public class TestUserManagerUnit {
    public static void main(String[] args) {
        System.out.println("=== 单元测试开始 ===");
        int passed = 0;
        int failed = 0;

        // 测试1: 注册功能
        System.out.println("\n[测试1: 注册功能]");
        if (testRegistration()) {
            System.out.println("✓ 注册测试通过");
            passed++;
        } else {
            System.out.println("✗ 注册测试失败");
            failed++;
        }

        // 测试2: 登录功能
        System.out.println("\n[测试2: 登录功能]");
        if (testLogin()) {
            System.out.println("✓ 登录测试通过");
            passed++;
        } else {
            System.out.println("✗ 登录测试失败");
            failed++;
        }

        // 测试3: 游客功能
        System.out.println("\n[测试3: 游客功能]");
        if (testGuest()) {
            System.out.println("✓ 游客测试通过");
            passed++;
        } else {
            System.out.println("✗ 游客测试失败");
            failed++;
        }

        // 测试4: 登出功能
        System.out.println("\n[测试4: 登出功能]");
        if (testLogout()) {
            System.out.println("✓ 登出测试通过");
            passed++;
        } else {
            System.out.println("✗ 登出测试失败");
            failed++;
        }

        // 测试5: 边界情况
        System.out.println("\n[测试5: 边界情况]");
        if (testEdgeCases()) {
            System.out.println("✓ 边界测试通过");
            passed++;
        } else {
            System.out.println("✗ 边界测试失败");
            failed++;
        }

        // 统计结果
        System.out.println("\n=== 测试结果 ===");
        System.out.println("通过: " + passed);
        System.out.println("失败: " + failed);
        System.out.println("总计: " + (passed + failed));
        System.out.println("成功率: " + (passed * 100 / (passed + failed)) + "%");
    }

    private static boolean testRegistration() {
        UserManager um = new UserManager();

        // 1. 正常注册
        boolean result1 = um.register("user1", "pass1");
        if (!result1) return false;

        // 2. 重复注册应该失败
        boolean result2 = um.register("user1", "pass2");
        if (result2) return false; // 应该失败

        // 3. 新用户注册应该成功
        boolean result3 = um.register("user2", "pass2");
        if (!result3) return false;

        return true;
    }

    private static boolean testLogin() {
        UserManager um = new UserManager();
        um.register("testuser", "testpass");
        um.logout();

        // 1. 正确密码登录
        boolean result1 = um.login("testuser", "testpass");
        if (!result1) return false;

        um.logout();

        // 2. 错误密码登录
        boolean result2 = um.login("testuser", "wrongpass");
        if (result2) return false; // 应该失败

        return true;
    }

    private static boolean testGuest() {
        UserManager um = new UserManager();

        // 游客登录
        um.loginAsGuest();
        if (um.getCurrentUser() == null || !um.getCurrentUser().isGuest()) {
            return false;
        }

        // 游客名应该以Guest_开头
        if (!um.getCurrentUser().getUsername().startsWith("Guest_")) {
            return false;
        }

        return true;
    }

    private static boolean testLogout() {
        UserManager um = new UserManager();
        um.register("logouttest", "pass");

        // 登出前应该有用户
        if (um.getCurrentUser() == null) return false;

        um.logout();

        // 登出后应该没有用户
        if (um.getCurrentUser() != null) return false;

        return true;
    }

    private static boolean testEdgeCases() {
        UserManager um = new UserManager();

        // 1. 空用户名注册应该失败
        boolean result1 = um.register("", "password");
        if (result1) return false;

        // 2. 空密码注册应该失败
        boolean result2 = um.register("username", "");
        if (result2) return false;

        // 3. 纯空格用户名应该失败
        boolean result3 = um.register("   ", "password");
        if (result3) return false;

        // 4. null参数应该失败
        boolean result4 = um.register(null, "password");
        if (result4) return false;

        return true;
    }
}