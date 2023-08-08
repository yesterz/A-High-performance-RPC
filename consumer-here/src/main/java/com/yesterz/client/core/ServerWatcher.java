package com.yesterz.client.core;

import com.yesterz.client.zookeeper.ZookeeperFactory;
import io.netty.channel.ChannelFuture;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import java.util.HashSet;
import java.util.List;

public class ServerWatcher implements CuratorWatcher {

    @Override
    public void process(WatchedEvent event) throws Exception {

        CuratorFramework client = ZookeeperFactory.create();
        String path = event.getPath();
        client.getChildren().usingWatcher(this).forPath(path);
        List<String> serverPaths = client.getChildren().forPath(path);


        ChannelManger.clear();
        for (String serverPath : serverPaths) {
            String[] str = serverPath.split("#");
            int weight = Integer.valueOf(str[2]);
            if (weight > 0) {
                for (int w = 0; w <= weight; w++) {
                    ChannelManger.realServerPath.add(str[0] + "#" + str[1]);
                }
            }
            ChannelManger.realServerPath.add(str[0] + "#" + str[1]);
        }

        ChannelManger.clear();
        for (String realServer : ChannelManger.realServerPath) {
            String[] str = realServer.split("#");
            try {
                int weight = Integer.valueOf(str[2]);
                if (weight > 0) {
                    for (int w = 0; w <= weight; w++) {
                        ChannelFuture channelFuture = TcpClient.b.connect(str[0], Integer.valueOf(str[1]));
                        ChannelManger.add(channelFuture);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // 重新建立连接，写入
    }
}
