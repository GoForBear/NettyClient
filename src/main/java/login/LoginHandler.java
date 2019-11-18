package login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

public class LoginHandler extends ChannelInboundHandlerAdapter {
    public static final LoginHandler instance = new LoginHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(null == msg){
            super.channelRead(ctx,msg);
            return;
        }
        ByteBuf byteBuf = (ByteBuf) msg;
        int login = byteBuf.readInt();
        System.out.println("clinet is:" + login);
        if(1 == login){
            ChannelPipeline channelPipeline = ctx.pipeline();
            channelPipeline.remove(this);
            System.out.println("登录成功");
        }else{
            System.out.println("登陆失败");
        }
//        super.channelRead(ctx, msg);
    }



}
