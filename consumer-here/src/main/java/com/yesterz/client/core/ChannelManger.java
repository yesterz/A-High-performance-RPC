package com.yesterz.client.core;

import io.netty.channel.ChannelFuture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChannelManger {

    static CopyOnWriteArrayList<String> realServerPath = new CopyOnWriteArrayList<>();
    static AtomicInteger position = new AtomicInteger(0);


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


    public static ChannelFuture get(AtomicInteger i) {
        int size = channelFutures.size();
        ChannelFuture channel = null;
        if (i.get() > size) {
            channel = channelFutures.get(0);
            ChannelManger.position = new AtomicInteger(1);
        } else {
            channel = channelFutures.get(i.getAndIncrement());
        }

        return channel;
    }
}
