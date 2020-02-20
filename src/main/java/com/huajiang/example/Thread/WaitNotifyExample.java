package com.huajiang.example.Thread;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 上午8:43
 * @Copyright
 */
public class WaitNotifyExample {

    public synchronized void before(){
        System.out.println("before");
        //notify(); //唤醒一个
        notifyAll(); //唤醒所有等待进程
    }

    public synchronized void after() {
        try{
            wait(); //让当前进程等待，并会被挂起
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("after");
    }

    public synchronized void middle(){
        try{
            wait(); //让当前进程等待，并会被挂起
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("middle");
    }
}
