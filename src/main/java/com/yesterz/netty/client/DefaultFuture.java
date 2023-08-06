package com.yesterz.netty.client;

import com.yesterz.netty.util.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DefaultFuture {

    public final static ConcurrentHashMap<Long, DefaultFuture> allDefautlFuture = new ConcurrentHashMap<Long, DefaultFuture>();
    final Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();
    private Response response;



    public DefaultFuture(ClientRequest request) {
        allDefautlFuture.put(request.getId(), this);
    }

    public static void receive(Response response) {
        DefaultFuture defaultFuture = allDefautlFuture.get(response.getId());
        if (defaultFuture != null) {
            Lock lock = defaultFuture.lock;
            lock.lock();
            try {
                defaultFuture.setResponse(response);
                defaultFuture.condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
    // 主线程获取数据，首先要等待结果，
    public Response get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

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

}
