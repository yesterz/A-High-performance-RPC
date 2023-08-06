package com.yesterz.netty.medium;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.netty.handler.param.ServerRequest;
import com.yesterz.netty.util.Response;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Medium {
    public static Map<String, BeanMethod> beanMap;
    static {
        beanMap = new HashMap<String, BeanMethod>();
    }

    private static Medium medium = null;
    private Medium() {

    }

    public static Medium newInstance() {
        if (medium == null) {
            medium = new Medium();
        }
        return medium;
    }

    // 非常关键的方法a，这里用反射处理业务
    public Response process(ServerRequest serverRequest) {

        Response result = null;

        try {
            // command是key
            String command = serverRequest.getCommand();
            BeanMethod beanMethod = beanMap.get(command);
            if (beanMethod == null) {
                return null;
            }

            Object bean = beanMethod.getBean();
            Method method = beanMethod.getMethod();
            // 先只实现一个参数
            Class paramType = method.getParameterTypes()[0];
            System.out.println("paramType " + paramType);
            Object content = serverRequest.getContent();
            System.out.println("serverRequest.getContent() " + content.toString());

            // 先假设参数是一个简单的bean
            Object args = JSONObject.parseObject(JSONObject.toJSONString(content), paramType);

            result = (Response) method.invoke(bean, args);
            System.out.println("result.getResult() " + result.getResult());
            result.setId(serverRequest.getId());
            System.out.println("Medium.java's result.getId() " + result.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
