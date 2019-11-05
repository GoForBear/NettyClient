import login.userLogin;

import java.util.Scanner;

public class Client {


    private void initSocket(){

    }



    private void run(){
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入你的指令");
            while(sc.hasNext()){
                String next = sc.next();
                if("login".equals(next)){
                    Scanner loginSc = new Scanner(System.in);
                    System.out.println("请输入你的用户名");
                    String userName = loginSc.nextLine();
                    System.out.println("请输入你的密码");
                    String passWord = loginSc.nextLine();
                    userLogin.login(userName,passWord);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
