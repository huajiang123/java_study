package com.huajiang.example.Thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.Thread
 * @date 2020/2/11 下午5:05
 * @Copyright
 */
public class ThreadUnsafeExample {

    //AtomicInteger 能保证多个线程修改的原子性。 Atomicxxxxx
    private AtomicInteger cnt = new AtomicInteger();

   /* public synchronized  void add(){
        cnt++;
    }*/
   public void add(){
       cnt.incrementAndGet();
   }
    public int get(){
        return cnt.get();
    }
}
