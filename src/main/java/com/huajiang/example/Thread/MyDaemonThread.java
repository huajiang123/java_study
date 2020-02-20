package com.huajiang.example.Thread;

/**
 * 守护线程
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 上午11:55
 * @Copyright
 */
public class MyDaemonThread implements Runnable {
    public void run() {
        System.out.println("this is MyDaemonThread");
    }
}
