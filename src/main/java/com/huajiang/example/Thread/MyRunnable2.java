package com.huajiang.example.Thread;

import java.util.concurrent.Executor;

import static java.lang.Thread.interrupted;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/10 下午1:43
 * @Copyright
 */
public class MyRunnable2 implements Runnable {

    public void run() {

        /**
         * 如果一个线程的 run() 方法执行一个无限循环，并且没有执行 sleep() 等
         * 会抛出 InterruptedException 的操作，
         * 那么调用线程的 interrupt() 方法就无法使线程提前结束.
         * 但是调用 interrupt() 方法会设置线程的中断标记，此时调用 interrupted() 方法会返回 true。
         * 因此可以在循环体中使用 interrupted() 方法来判断线程是否处于中断状态，从而提前结束线程
         */
        while (!interrupted()){
            System.out.println("this is MyRunnable2");
        }
        System.out.println("this is MyRunnable2 End");
    }
}
