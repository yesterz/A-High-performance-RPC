package com.yesterz.client.core;

import com.yesterz.client.zookeeper.ZookeeperFactory;
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
        for (String serverPath : serverPaths) {
            TcpClient.realServerPath.add(serverPath.split("#")[0]);
        }


    }
}
