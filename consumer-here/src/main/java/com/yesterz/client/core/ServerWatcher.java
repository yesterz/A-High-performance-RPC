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
        client.getChildren().usingWatcher(this);
        List<String> serverPaths = client.getChildren().forPath(path);

        TcpClient.realServerPath = new HashSet<String>();
        ChannelManger.clear();

        for (String serverPath : serverPaths) {
            String[] str = serverPath.split("#");

            TcpClient.realServerPath.add(str[0] + "#" +  str[1]);

            ChannelFuture channelFuture = TcpClient.b.connect(str[0], Integer.valueOf(str[1]));
            ChannelManger.add(channelFuture);

        }

        // 重新建立连接，写入



    }
}
