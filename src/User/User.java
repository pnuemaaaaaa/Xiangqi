package User;

import java.time.LocalDateTime;

public class User {
    private String username;
    private String passwordHash;  // 存储加密后的密码
    private boolean isGuest;
    private LocalDateTime registerDate;
    private LocalDateTime lastLogin;

    // 构造方法
    public User(String username, String passwordHash, boolean isGuest) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.isGuest = isGuest;
        this.registerDate = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    // 游客用户构造方法
    public static User createGuestUser() {
        User guest = new User("Guest_" + System.currentTimeMillis(), "", true);
        guest.setGuest(true);
        return guest;
    }

    // 检查密码是否正确（简化版）
    public boolean checkPassword(String inputPassword) {
        return this.passwordHash.equals(inputPassword);
    }

    //更新登录时间
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
