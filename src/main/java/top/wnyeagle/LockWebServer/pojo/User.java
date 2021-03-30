package top.wnyeagle.LockWebServer.pojo;

import top.wnyeagle.LockWebServer.util.AuthorityException;

/**
 * @Description
 * @ClassName User
 * @Author wny
 * @Date 2021/3/5 12:04
 * @Version 1.0
 */

public class User {
    public static final String MANAGER = "manager";
    public static final String USER="user";


    private Integer userId;
    private String userName;
    private String password;
    private Integer age;
    private Integer schoolNumber;
    private String authority;
    private String email;

    public User() {
    }

    public User(Integer userId, String userName, String password, Integer age, Integer schoolNumber, String authority, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.schoolNumber = schoolNumber;
        this.authority = authority;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(Integer schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getAuthority() {
        return authority;
    }

    /**
     * @param authority 传入的参数类型必须是 MANAGER  USER 的值
     */
    public void setAuthority(String authority) {
        if (authority.equals(MANAGER)||authority.equals(USER))
        this.authority = authority;
        else {
            try {
                throw new AuthorityException("传入的权限参数类型符合规定");
            } catch (AuthorityException e) {
                e.printStackTrace();
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", schoolNumber=" + schoolNumber +
                ", authority='" + authority + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
