package User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private User currentUser;

    // 核心方法：
    // 初始化
    public UserManager() {
        this.users = new ArrayList<>();
        this.currentUser = null;
    }

    //注册
    public boolean register(String username,String password) {
        if(username == null || password == null) {
            System.out.println("注册失败：用户名或密码不能为空或纯空格");
            return false;
        }
        String newUsername = username.replaceAll("\\s+", "");
        String newPassword = password.replaceAll("\\s+", "");
        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            System.out.println("注册失败：用户名或密码不能为空或纯空格");
            return false;
        }


        //检查用户名是否已存在
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) {
                System.out.println("注册失败：用户名已存在");
                return false;
            }
        }

        //创建新用户并添加到列表
        boolean isGuest = false;
        User newUser = new User(newUsername, newPassword, isGuest);
        users.add(newUser);
        currentUser = newUser;

        System.out.println("注册成功: " + newUsername);
        return true;

    }


    //用户登录
    public boolean login(String username,String password) {
        // 1. 检查输入有效性
        if (username == null || password == null) {
            System.out.println("登录失败：用户名或密码不能为空");
            return false;
        }

        // 2. 检查是否已登录
        if (currentUser != null && !currentUser.isGuest()) {
            System.out.println("登录失败：请先登出当前用户");
            return false;
        }

        // 3. 去除所有空白字符并检查是否为空
        String newUsername = username.replaceAll("\\s+", "");
        String newPassword = password.replaceAll("\\s+", "");

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            System.out.println("登录失败：用户名或密码不能为空或纯空格");
            return false;
        }

        // 4. 查找用户并验证密码
        for (User user : users) {
            if (user.getUsername().equals(newUsername)) {
                if (user.checkPassword(newPassword)) {
                    currentUser = user;
                    user.updateLastLogin();
                    System.out.println("登录成功: " + newUsername);
                    return true;
                } else {
                    System.out.println("登录失败：密码错误");
                    return false;
                }
            }
        }

        System.out.println("登录失败：用户不存在");
        return false;
    }

    //游客登录
    public void loginAsGuest() {
        currentUser = User.createGuestUser();
        System.out.println("游客登录: " + currentUser.getUsername());
    }

    //登出
    public void logout() {
        if (currentUser != null) {
            System.out.println("用户登出: " + currentUser.getUsername());
            currentUser = null;
        } else {
            System.out.println("当前没有用户登录");
        }
    }
    // 获取当前用户
    public User getCurrentUser() {
        return currentUser;
    }









    // 获取所有用户（调试用）
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // 添加一些测试用户（暂时用）
    public void addTestUsers() {
        users.add(new User("test1", "123456", false));
        users.add(new User("test2", "abcdef", false));
        System.out.println("添加了2个测试用户");
    }


}