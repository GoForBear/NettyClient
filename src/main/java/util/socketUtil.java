package util;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import login.loginHandler;

public class socketUtil {

    private static Bootstrap clientBootstrap = new Bootstrap();
    private static ChannelFuture channelFuture = initSocket();

    public static ChannelFuture initSocket(){
        EventLoopGroup clientGroup  = new NioEventLoopGroup();
        try{
            clientBootstrap.group(clientGroup);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(Config.SOCKET_SERVER_IP,Config.SOCKET_SERVER_PORT);
            clientBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(loginHandler.instance);
                }
            });
            channelFuture = clientBootstrap.connect();
            ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("连接成功");
                    }else{
                        System.out.println("连接失败");
                    }

                }
            };
            channelFuture.addListener(channelFutureListener);

        }catch (Exception e){

        }
        return channelFuture;
    }

    public static boolean connect(String userName, String passWord) throws Exception{
        Channel channel = channelFuture.channel();
        byte[] bytes = (userName + "," + passWord).getBytes("UTF-8");
        ByteBuf bytebuf = channel.alloc().buffer();
        ((ByteBuf) bytebuf).writeBytes(bytes);
        channel.writeAndFlush(bytebuf);
        System.out.println("发送登录信息");


        return true;
    }
}
