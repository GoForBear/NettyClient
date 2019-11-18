package session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class ClientSession {
    public static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
    private Channel channel;
    private String sessionId;
    private boolean isConnected = false;
    private boolean isLogin = false;

    public ClientSession(Channel channel){
        this.channel = channel;
        this.sessionId = String.valueOf(-1);
        channel.attr(ClientSession.SESSION_KEY).set(this);
    }

    public static  void loginSuccess(ChannelHandlerContext context, Object msg){
        Channel channel = context.channel();
        ClientSession clientSession = channel.attr(ClientSession.SESSION_KEY).get();
        clientSession.setSessionId(msg.toString());
        clientSession.setLogin(true);
    }

    public static ClientSession getSession(ChannelHandlerContext context){
        Channel channel = context.channel();
        ClientSession clientSession = channel.attr(ClientSession.SESSION_KEY).get();
        return clientSession;
    }

    public String getRemoteAddress(){
        return channel.remoteAddress().toString();
    }

    public void close(){
        isConnected = false;
        ChannelFuture channelFuture = channel.close();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("连接已经断开");
                }
            }
        });
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
