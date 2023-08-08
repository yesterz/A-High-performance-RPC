package com.yesterz.client.core;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.client.constant.Constants;
import com.yesterz.client.handler.SimpleClientHandler;
import com.yesterz.client.param.ClientRequest;
import com.yesterz.client.param.Response;
import com.yesterz.client.zookeeper.ZookeeperFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.Watcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TcpClient {

    static Set<String> realServerPath = new HashSet<String>();

    static final Bootstrap b = new Bootstrap();
    static ChannelFuture f = null;

    static {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup); // (2)
        b.channel(NioSocketChannel.class); // (3)
        b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new SimpleClientHandler());
                ch.pipeline().addLast(new StringEncoder());
            }
        });

        CuratorFramework client = ZookeeperFactory.create();
        String host = "localhost";
        int port = 8080;
        try {
            List<String> serverParths = client.getChildren().forPath(Constants.SERVER_PATH);

            // 加上zookeeper监听服务器的变化
            CuratorWatcher watcher = new ServerWatcher();
            client.getChildren().usingWatcher(watcher).forPath(Constants.SERVER_PATH);

            for (String serverPath : serverParths) {
                String[] str = serverPath.split("#");
                realServerPath.add(str[0] + "#" +  str[1]);

                ChannelFuture channelFuture = TcpClient.b.connect(str[0], Integer.valueOf(str[1]));
                ChannelManger.add(channelFuture);
            }
            if (realServerPath.size() > 0) {
                String[] hostAndPort = realServerPath.toArray()[0].toString().split("#");
                host = hostAndPort[0];
                port = Integer.valueOf(hostAndPort[1]);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        try {
////            f = b.connect(host, port).sync();
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }

    static int i = 0;




    // 注意：1点每一个请求都是同一个连接，并发问题
    // 发送数据
    // Request 1、唯一请求id 2、请求内容
    // Response 1、响应唯一识别id 2、响应结果
    public static Response send(ClientRequest request) {
        f=ChannelManger.get(i);
        f.channel().writeAndFlush(JSONObject.toJSONString(request));
        f.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(request);

        return df.get(i);
    }

}
