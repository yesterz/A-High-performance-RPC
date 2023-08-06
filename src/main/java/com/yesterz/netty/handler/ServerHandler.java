package com.yesterz.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.netty.util.Response;
import com.yesterz.netty.handler.param.ServerRequest;
import com.yesterz.netty.medium.Medium;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.channel().writeAndFlush("It's ok\r\n");
//         ctx.flush();
//        ctx.channel().close();
        ServerRequest serverRequest = JSONObject.parseObject(msg.toString(), ServerRequest.class);
        System.out.println(serverRequest.getCommand());

        // 交给medium去处理
        Medium medium = Medium.newInstance();
        Response response = medium.process(serverRequest);

//        更新了返回结果为Response对象就不用这些了
//        Response resp = new Response();
//        resp.setId(serverRequest.getId());
        System.out.println("Received msg is " + msg);
//        resp.setResult("is OK from " + this.getClass().getName());

        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("读空闲===");
                ctx.channel().close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("写空闲===");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("读写空闲");
                ctx.channel().writeAndFlush("ping\r\n");
            }
        }
    }
}
