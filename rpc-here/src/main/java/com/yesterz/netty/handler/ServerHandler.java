package com.yesterz.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.netty.handler.param.ServerRequest;
import com.yesterz.netty.medium.Medium;
import com.yesterz.netty.util.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ServerRequest request = JSONObject.parseObject(msg.toString(), ServerRequest.class);
        System.out.println("request.getCommand() " + request.getCommand());

        // 交给medium去处理
        Medium medium = Medium.newInstance();
        Response result = medium.process(request);
        System.out.println("medium.process(request) " + result.getId());

//        更新了返回结果为Response对象就不用这些了
//        Response resp = new Response();
//        resp.setId(serverRequest.getId());
        System.out.println("Received msg is " + msg);
//        System.out.println(JSONObject.toJSONString(response));
//        resp.setResult("is OK from " + this.getClass().getName());

        // 向客户端发送响应 Response
        ctx.channel().writeAndFlush(JSONObject.toJSONString(result));
        ctx.channel().writeAndFlush("\r\n");
    }
}
