import Task.ConnectTask;
import Task.FutureConnectTaskThread;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.GenericFutureListener;
import login.LoginConsole;
import sender.LoginSender;
import session.ClientSession;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {

    private ClientSession session;
    private ClientConnect clientConnect = new ClientConnect();
    private LoginSender loginSender = new LoginSender();
    private Channel channel;
    private boolean connectFlag = false;
    GenericFutureListener<ChannelFuture> connectListner = new GenericFutureListener<ChannelFuture>() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            final EventLoopGroup eventLoopGroup = channelFuture.channel().eventLoop();
            if(! channelFuture.isSuccess()){
                System.out.println("连接失败,10s后尝试重新连接");
                eventLoopGroup.schedule(new Runnable() {
                    @Override
                    public void run() {
                        clientConnect.Connect();
                    }
                },10, TimeUnit.SECONDS);
                connectFlag = false;
            }else{
                connectFlag = true;
                System.out.println("连接成功");
                channel = channelFuture.channel();
                session = new ClientSession(channel);
                session.setConnected(true);
                channel.closeFuture().addListener(closeListner);
                notifyCommandThread();

            }
        }
    };

    GenericFutureListener<ChannelFuture> closeListner = new GenericFutureListener<ChannelFuture>() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            System.out.println("连接关闭");
            channel = channelFuture.channel();
            ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
            session.close();
            notifyCommandThread();

        }
    };

    public void start(){
        Thread.currentThread().setName("命令线程");

        while(true){
            while(connectFlag == false){
                startConnect();
                waitTherad();
            }

            while(null != session && session.isConnected()){
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入你的指令");
                String command = scanner.nextLine();
                switch(command){
                    case "login":
                        LoginConsole loginConsole = new LoginConsole();
                        login(loginConsole.beginLogin());
                        break;
                    default:
                        System.out.println("没有这个指令");
                        break;
                }
            }

        }
    }

    private void startConnect(){
        FutureConnectTaskThread.add(new ConnectTask() {
            @Override
            public void excute() {
                clientConnect.setConnectListner(connectListner);
                clientConnect.Connect();
            }
        });
    }

    private synchronized void notifyCommandThread(){
        this.notify();
    }

    public synchronized  void waitTherad(){
        try{
            this.wait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void login(LoginConsole loginConsole){
        if(!connectFlag){
            System.out.println("连接异常，请重新连接");
            return;
        }
        loginSender.setSession(session);
        loginSender.senMsg(loginConsole);

    }

}
