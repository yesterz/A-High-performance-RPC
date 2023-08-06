package com.yesterz.netty.medium;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.netty.handler.param.ServerRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Medium {
    public static Map<String, BeanMethod> beanMap;
    static {
        beanMap = new HashMap<String, BeanMethod>();
    }

    private static Medium m = null;
    private Medium() {

    }

    public static Medium newInstance() {
        if (m == null) {
            m = new Medium();
        }
        return m;
    }

    // 非常关键的方法a，这里用反射处理业务
    public Object process(ServerRequest serverRequest) {

        Object result = null;

        try {
            String command = serverRequest.getCommand();
            BeanMethod beanMethod = beanMap.get(command);
            if (beanMethod == null) {
                return null;
            }

            Object bean = beanMethod.getBean();
            Method m = beanMethod.getMethod();
            Class paramType = m.getParameterTypes()[0];
            Object content = serverRequest.getContent();

            // 先假设参数是一个简单的bean
            Object args = JSONObject.parseObject(JSONObject.toJSONString(content), paramType);


            result = m.invoke(bean, args);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
