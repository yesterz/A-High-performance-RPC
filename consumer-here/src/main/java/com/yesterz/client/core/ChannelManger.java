package com.yesterz.client.core;

import io.netty.channel.ChannelFuture;

import java.util.concurrent.CopyOnWriteArrayList;

public class ChannelManger {

    // 同时可以读写，普通的类有并发问题
    public static CopyOnWriteArrayList<ChannelFuture> channelFutures = new CopyOnWriteArrayList<>();

    public static void removeChannel(ChannelFuture channel) {
        channelFutures.remove(channel);
    }

    public static void add(ChannelFuture channel) {
        channelFutures.add(channel);
    }

    public static void clear() {
        channelFutures.clear();
    }


    public static ChannelFuture get(int i) {
        int size = channelFutures.size();
        ChannelFuture channel = null;
        if (i > size) {
            channel = channelFutures.get(0);
            i = 1;
        } else {
            channel = channelFutures.get(i);
            i++;
        }

        return channel;
    }
}
