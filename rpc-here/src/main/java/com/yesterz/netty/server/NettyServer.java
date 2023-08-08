package com.yesterz.netty.server;

import com.yesterz.netty.constant.Constants;
import com.yesterz.netty.factory.ZookeeperFactory;
import com.yesterz.netty.handler.SimpleServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new IdleStateHandler(6, 4,2, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new SimpleServerHandler());
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });
            int port = 8081;
            int weight = 1;
            ChannelFuture future = bootstrap.bind(port).sync();
            CuratorFramework client = ZookeeperFactory.create();
//            InetAddress inetAddress = InetAddress.getLocalHost();
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
//            client.create().forPath(Constants.SERVER_PATH);
            System.out.println(inetAddress.getHostAddress());
            client.create()
                  .creatingParentsIfNeeded()
                  .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                  .forPath(Constants.SERVER_PATH + "/" + inetAddress.getHostAddress() + "#" + port + "#" + weight + "#");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
