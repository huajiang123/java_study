package com.huajiang.example.Thread;

import java.util.concurrent.BlockingQueue;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 下午4:17
 * @Copyright
 */
public class Consumer extends Thread {
    private BlockingQueue queue;

    public Consumer(BlockingQueue queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            queue.put("product");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("consum...."+" ");
    }
}

