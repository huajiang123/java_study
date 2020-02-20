package com.huajiang.example.Thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 下午4:04
 * @Copyright
 */
public class Producer extends Thread {
    private BlockingQueue queue;

    public Producer(BlockingQueue queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            queue.put("product");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("produce...."+" ");
    }
}
