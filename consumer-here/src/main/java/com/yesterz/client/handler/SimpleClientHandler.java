package com.yesterz.client.handler;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.client.core.DefaultFuture;
import com.yesterz.client.param.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ("ping".equals(msg.toString())) {
            ctx.channel().writeAndFlush("pong\r\n");
            return ;
        }
//        System.out.println(msg.toString());
//        ctx.channel().attr(AttributeKey.valueOf("ssssss")).set(msg);
        // 设置响应内容
        System.out.println("SimpleClientHandler.java's msg.toString() " + msg.toString());
        Response response = JSONObject.parseObject(msg.toString(), Response.class);
        // 通过response的ID可以在map中找到对应的Request,并为相应的request设置response,使得调用get()客户端得到结果
        System.out.println("SimpleClientHandler.java's response.getResult() " + response.getResult());
        DefaultFuture.receive(response);
//        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }
}
