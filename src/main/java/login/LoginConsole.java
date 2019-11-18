package login;

import java.util.Scanner;

public class LoginConsole {
    private String userName;
    private String passWord;


    public LoginConsole beginLogin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的用户名");
        this.userName = scanner.nextLine();
        System.out.println("请输入你的密码");
        this.passWord = scanner.nextLine();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
