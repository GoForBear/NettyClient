package sender;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import login.LoginConsole;
import session.ClientSession;

import java.nio.ByteBuffer;

public class LoginSender {
    private ClientSession session;

    private boolean isConnected(){
        if(null == session){
            System.out.println("没有连接");
            return false;
        }
        return true;
    }

    public void senMsg(LoginConsole loginConsole){
        if(null == getSession() || !isConnected()){
            return;
        }
        //非字符串的写入方式
//        byte[] bytes = ByteBuffer.allocate(4).putInt((login == false) ? 0 : 1).array();
//        ByteBuf outbuf = ByteBufAllocator.DEFAULT.buffer(4);
//        outbuf.writeBytes(bytes);
        byte[] bytes = null;
        try{
            bytes = (loginConsole.getUserName() + "," + loginConsole.getPassWord()).getBytes("UTF-8");
        }catch (Exception e){
            System.out.println("字符串转编码出现问题");
            e.printStackTrace();
        }
        Channel channel = getSession().getChannel();
        ByteBuf bytebuf = channel.alloc().buffer();
        ((ByteBuf) bytebuf).writeBytes(bytes);
        ChannelFuture channelFuture = channel.writeAndFlush(bytebuf);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("发送成功");
                }
            }
        });


    }


    public ClientSession getSession() {
        return session;
    }

    public void setSession(ClientSession session) {
        this.session = session;
    }
}
