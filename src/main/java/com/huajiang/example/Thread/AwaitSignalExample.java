package com.huajiang.example.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 上午9:40
 * @Copyright
 */
public class AwaitSignalExample {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void before(){
        lock.lock();
        try{
            System.out.println("before");
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public void after() {
        lock.lock();
        try {
            condition.await();
            System.out.println("after");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        finally {
            lock.unlock();
        }
    }
}
