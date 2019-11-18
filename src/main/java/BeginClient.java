public class BeginClient {

    public static void main(String[] args) {
        Client client = new Client();
        try{
            client.start();
        }catch (Exception e){
            System.out.println("客户端启动出现异常");
            e.printStackTrace();
        }

    }
}
