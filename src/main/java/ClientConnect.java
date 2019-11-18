import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import login.LoginHandler;
import util.Config;

public class ClientConnect {

    private GenericFutureListener<ChannelFuture> connectListner;
    private Bootstrap bootstrap;
    private EventLoopGroup clientGroup;


    public ClientConnect(){
        clientGroup = new NioEventLoopGroup();
    }

    public void Connect(){
        try{
            bootstrap = new Bootstrap();
            bootstrap.group(clientGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(Config.SOCKET_SERVER_IP,Config.SOCKET_SERVER_PORT);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(LoginHandler.instance);
                }
            });
            System.out.println("客户端开始连接");
            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener(connectListner);

        }catch (Exception e){
            System.out.println("客户端连接失败");
            e.printStackTrace();
        }

    }

    public void close(){
        clientGroup.shutdownGracefully();
    }

    public GenericFutureListener<ChannelFuture> getConnectListner() {
        return connectListner;
    }

    public void setConnectListner(GenericFutureListener<ChannelFuture> connectListner) {
        this.connectListner = connectListner;
    }
}
