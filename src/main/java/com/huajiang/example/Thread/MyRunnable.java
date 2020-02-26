package com.huajiang.example.Thread;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 上午11:18
 * @Copyright
 */
public class MyRunnable implements Runnable {

    public void run() {
        /**
         * Thread.sleep(millisec) 方法会休眠当前正在执行的线程，millisec 单位为毫秒。
         *sleep() 可能会抛出 InterruptedException，
         * 因为异常不能跨线程传播回 main() 中，因此必须在本地进行处理。
         * 线程中抛出的其它异常也同样需要在本地进行处理
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is MyRunnable");
        /**
         * 对静态方法 Thread.yield() 的调用声明了当前线程已经完成了生命周期中最重要的部分，
         * 可以切换给其它线程来执行。该方法只是对线程调度器的一个建议，
         * 而且也只是建议具有相同优先级的其它线程可以运行。
         * 将当前进程的状态转换为就绪状态
         */
        Thread.yield();
        System.out.println("this is MyRunnable after yield");
    }
}
