package com.huajiang.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.proxy
 * @date 2020/2/26 下午3:50
 * @Copyright
 */
public class ProxyHandler implements InvocationHandler {

    private  Object tar;
    //返回一个tar的代理对象
    public Object bind(Object tar){
        this.tar = tar;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(),tar.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = new Object();
        //可以织入方法
        System.out.println("before invoke");
        result = method.invoke(tar,args);
        //织入方法
        System.out.println("after invoke");
        return result;
    }
}
