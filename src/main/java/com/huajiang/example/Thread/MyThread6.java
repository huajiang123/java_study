package com.huajiang.example.Thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 下午2:43
 * @Copyright
 */
public class MyThread6 extends Thread {

    private CyclicBarrier cyclicBarrier;

    public MyThread6(CyclicBarrier barrier){
        this.cyclicBarrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread6 has arrived,await().......");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread4 continue to gooooooooo");
    }
}
