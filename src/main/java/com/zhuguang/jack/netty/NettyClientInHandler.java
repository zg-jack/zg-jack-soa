package com.zhuguang.jack.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientInHandler extends ChannelInboundHandlerAdapter {
    
    private StringBuffer message;
    
    private String sendmsg;
    
    public NettyClientInHandler(StringBuffer message, String sendmsg) {
        this.message = message;
        this.sendmsg = sendmsg;
    }
    
    @Override
    public boolean isSharable() {
        // TODO Auto-generated method stub
        return super.isSharable();
    }
    
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel--register================");
    }
    
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel--unregister================");
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel--Active================");
        
        ByteBuf buffer = ctx.alloc().buffer(4 * sendmsg.length());
        buffer.writeBytes(sendmsg.getBytes());
        ctx.write(buffer);
        Thread.sleep(10000);
        ctx.flush();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============channel--Inactive================");
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("==============channel--channelRead================");
        ByteBuf result = (ByteBuf)msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        System.out.println("server retuan : " + new String(result1));
        message.append(new String(result1));
        result.release();
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelReadComplete(ctx);
    }
}
