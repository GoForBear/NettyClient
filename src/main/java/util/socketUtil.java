package util;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class socketUtil {

    private static Bootstrap clientBootstrap;
    private static ChannelFuture channelFuture;

    public socketUtil(){
        initSocket();
    }

    private boolean initSocket(){
        EventLoopGroup clientGroup  = new NioEventLoopGroup();
        try{
            clientBootstrap.group(clientGroup);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(Config.SOCKET_SERVER_IP,Config.SOCKET_SERVER_PORT);
            clientBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {

                }
            });
            channelFuture = clientBootstrap.connect();
            ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){

                    }else{

                    }

                }
            };
            channelFuture.addListener(channelFutureListener);

        }catch (Exception e){

        }
        return true;
    }

    public boolean connect(){
        Channel channel = channelFuture.channel();


        return true;
    }
}
