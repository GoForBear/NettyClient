package login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class loginHandler extends ChannelInboundHandlerAdapter {
    public static final loginHandler instance = new loginHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] arr = new byte[len];
        byteBuf.getBytes(0,arr);
        System.out.println("clinet is:" + new String(arr,"UTF-8"));
        super.channelRead(ctx, msg);
    }
}
