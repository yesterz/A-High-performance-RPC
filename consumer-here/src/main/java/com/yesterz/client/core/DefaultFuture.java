package com.yesterz.client.core;

import com.yesterz.client.param.ClientRequest;
import com.yesterz.client.param.Response;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DefaultFuture {

    public final static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<Long, DefaultFuture>();
    final Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Response response;
    private long timeout=2*60*1000l;
    private long startTime=System.currentTimeMillis();



    public DefaultFuture(ClientRequest request) {
        allDefaultFuture.put(request.getId(), this);
    }

    public static void receive(Response response) {

        System.out.println("Received response with ID: " + response.getId());

        DefaultFuture defaultFuture = allDefaultFuture.get(response.getId());
        if (defaultFuture != null) {
            Lock lock = defaultFuture.lock;
            lock.lock();
            try {
                System.out.println("Found corresponding DefaultFuture");
                defaultFuture.setResponse(response);
                defaultFuture.condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("No corresponding DefaultFuture found");
        }

    }
    // 主线程获取数据，首先要等待结果，
    public Response get(long time) {
        lock.lock();
        try {
            while (!done()) {
                condition.await(time, TimeUnit.MILLISECONDS);
                if ((System.currentTimeMillis() - startTime) > time) {
                    System.out.println("请求超时！");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        System.out.println(response.getResult());
        return this.response;
    }

    private boolean done() {
        if (this.response != null) {
            return true;
        }

        return false;
    }


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getStartTime() {
        return startTime;
    }

    static class FutureThread extends Thread {
        @Override
        public void run() {
            Set<Long> ids = allDefaultFuture.keySet();
            for (Long id : ids) {
                DefaultFuture df = allDefaultFuture.get(id);
                if (df==null) {
                    allDefaultFuture.remove(df);
                } else {
                    // 假如链路超时
                    if (df.getTimeout()<System.currentTimeMillis()-df.getStartTime()) {
                        Response resp = new Response();
                        resp.setId(id);
                        resp.setCode("333333");
                        resp.setMsg("链路请求超时");
                        DefaultFuture.receive(resp);
                    }
                }
            }
        }
    }

    static {
        FutureThread futureThread = new FutureThread();
        // 把这个线程设置为后台守护线程，就会去跑这个方法
        futureThread.setDaemon(true);
    }
}
