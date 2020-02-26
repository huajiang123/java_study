package com.huajiang.example.proxy;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.proxy
 * @date 2020/2/26 下午3:48
 * @Copyright
 */
public class RealSubject implements Subject {

    @Override
    public void sayHello() {
        System.out.println("this is RealSubject!");
    }
}
