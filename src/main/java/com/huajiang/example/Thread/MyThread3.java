package com.huajiang.example.Thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 上午11:08
 * @Copyright
 */
public class MyThread3 extends Thread{
    private CountDownLatch latch;
    public MyThread3 (CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is MyThread3,please hold on,i will be there after 3000 milseconds");
        latch.countDown();
    }
}
