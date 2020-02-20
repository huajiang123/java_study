import com.huajiang.example.Thread.*;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/10 上午11:22
 * @Copyright
 */
public class ThreadTest {

    @Test
    public void test_01() throws InterruptedException {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        //让本测试线程休眠,让MyRunnable()能够执行
        Thread.sleep(5000);

    }

    @Test
    public void test_02() throws ExecutionException,InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<String>(myCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }

    @Test
    public void test_03(){
        MyThread  myThread = new MyThread();
        myThread.start();
        myThread.run();
    }

    /**
     * Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。
     * 这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。
     * 主要有三种 Executor：
     *     CachedThreadPool：一个任务创建一个线程；
     *     FixedThreadPool：所有任务只能使用固定大小的线程；
     *     SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
     */
    // TODO: 2020/2/10 Executor原理
    @Test
    public void test_04(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i =0;i<5;i++){
            executorService.execute(new MyRunnable());
        }
        executorService.shutdown();
    }

    /**
     * 守护线程是程序运行时在后台提供服务的线程，不属于程序中不可或缺的部分。
     *
     * 当所有非守护线程结束时，程序也就终止，同时会杀死所有守护线程。
     *
     * main() 属于非守护线程。
     *
     * 在线程启动之前使用 setDaemon() 方法可以将一个线程设置为守护线程。
     */
    @Test
    public void test_05(){
        Runnable runnable = new MyDaemonThread();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 通过调用一个线程的 interrupt() 来中断该线程，如果该线程处于阻塞、
     * 限期等待或者无限期等待状态，那么就会抛出 InterruptedException，从而提前结束该线程。
     * 但是不能中断 I/O 阻塞和 synchronized 锁阻塞
     * @throws InterruptedException
     */
    @Test
    public void test_06() throws InterruptedException {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        thread.interrupt();//中断进程
        System.out.println("this is test_06");
    }

    @Test
    public void test_07() throws InterruptedException{
        MyRunnable2 myRunnable2 = new MyRunnable2();
        Thread thread = new Thread(myRunnable2);
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();//中断进程
        System.out.println("this is test_07");
    }


    @Test
    public void test_08(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            try {
                Thread.sleep(2000); //todo Executor原理
                System.out.println("Thread Run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdownNow(); //todo Executor原理
        System.out.println("this is test_08");
    }

    /**
     * synchronized 同步代码块
     */
    @Test
    public void test_09() throws InterruptedException {
        SynchronizedExample synchronizedExample = new SynchronizedExample();
        SynchronizedExample synchronizedExample1 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread.sleep(5000);
        executorService.execute(synchronizedExample::func1); //todo lamda表达式
        executorService.execute(() ->synchronizedExample.func1());
        //0 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9
        executorService.execute(() ->synchronizedExample1.func1()); //todo todo 不能得到交替运行结果
    }

    /**
     * synchronized 同步方法
     */
    @Test
    public void test_10() throws InterruptedException {
        SynchronizedExample synchronizedExample = new SynchronizedExample();
        SynchronizedExample synchronizedExample1 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread.sleep(5000);
        executorService.execute(() ->synchronizedExample.func2());
        /*executorService.execute(() ->synchronizedExample.func2());*/
        //0 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9
        executorService.execute(() ->synchronizedExample.func2()); //todo 不能得到交替运行结果
    }

    /**
     * synchronized 同步一个类
     */
    @Test
    public void test_11(){
        SynchronizedExample e1 = new SynchronizedExample();
        SynchronizedExample e2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> e1.func3());
        executorService.execute(() -> e2.func3());
    }

    /**
     * 使用jdk实现的reentrantlock
     */
    @Test
    public void test_12() throws InterruptedException {
        LockExample lockExample = new LockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread.sleep(5000);
        executorService.execute(() -> lockExample.func());
        executorService.execute(() -> lockExample.func());
    }

    @Test
    public void test_13() throws InterruptedException {
        JoinExample joinExample = new JoinExample();
        joinExample.test();
        Thread.sleep(5000);
    }

    @Test
    public void test_14() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
        service.execute(() -> waitNotifyExample.after());
        service.execute(() -> waitNotifyExample.middle());
        service.execute(() -> waitNotifyExample.after());
        service.execute(() -> waitNotifyExample.after());
        service.execute(() -> waitNotifyExample.before());
        Thread.sleep(5000);
    }

    @Test
    public void test_15(){
        ExecutorService service = Executors.newCachedThreadPool();
        AwaitSignalExample example = new AwaitSignalExample();
        service.execute(() -> example.after());
        service.execute(() -> example.before());
    }

    @Test
    public void test_16() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new MyThread1(latch));
        service.execute(new MyThread2(latch));
        service.execute(new MyThread3(latch));
        latch.await(); //当前线程阻塞
        System.out.println("get ready,it is time to fly to touch the sky");
        service.shutdown();
    }

    @Test
    public void test_17() throws InterruptedException, BrokenBarrierException {
        MyBarrierAction myBarrierAction = new MyBarrierAction();
        CyclicBarrier barrier = new CyclicBarrier(4,myBarrierAction);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new MyThread4(barrier));
        service.execute(new MyThread5(barrier));
        service.execute(new MyThread6(barrier));
        barrier.await();
        Thread.sleep(5000);
        System.out.println("this is test_17,go go go");
        service.shutdown();
    }

    /**
     * test Semaphore
     */
    @Test
    public void test_18(){
        final int totalClient = 3;
        final int totalRequest = 10;
        Semaphore semaphore = new Semaphore(totalClient);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i =0;i<totalRequest;i++){
            service.execute(() ->{
                try {
                    semaphore.acquire();
                    System.out.println(semaphore.availablePermits()  + " ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }

            });
        }
        service.shutdown();
    }

    @Test
    public void test_19() throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i <100 ; i++) {
                 Thread.sleep(10);
                 result+=i;
                }
                return result;
            }
        });

        Thread computeThread = new Thread(futureTask);
        computeThread.start();
        Thread.sleep(5000); //当前线程等待结算结果，此时可以做其他事。
        System.out.println(futureTask.get()); //获取计算结果
    }

    //用阻塞队列实现生产者消费者问题
    @Test
    public void test_20(){
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        for (int i = 0; i <2 ; i++) {
            Producer producer = new Producer(queue);
            producer.start();
        }
        for (int i = 0; i <3 ; i++) {
            Consumer consumer = new Consumer(queue);
            consumer.start();
        }
        for (int i = 0; i <2 ; i++) {
            Producer producer = new Producer(queue);
            producer.start();
        }
    }

    // TODO: 2020/2/11
    @Test
    public void test_21() throws ExecutionException, InterruptedException {

        ForkJoinExample forkJoinExample = new ForkJoinExample(1,100);
        ForkJoinPool joinPool = new ForkJoinPool();
        Future future = joinPool.submit(forkJoinExample);
        System.out.println(future.get());
    }

    @Test
    public void test_22() throws InterruptedException {
        final int threadSize = 1000;
        ThreadUnsafeExample threadUnsafeExample = new ThreadUnsafeExample();
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            service.execute(() ->{
                threadUnsafeExample.add();
                countDownLatch.countDown();});
        }
        countDownLatch.await();
        service.shutdown();
        System.out.println(threadUnsafeExample.get());
    }

    @Test
    public void test_23() throws InterruptedException {
        AtomicInteger next = new AtomicInteger();
        next.incrementAndGet();
        ThreadLocal threadLocal = new ThreadLocal(); //todo ThreadLocal 具体实现
        Thread thread1 = new Thread(()->{
            threadLocal.set(1);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
            threadLocal.remove();
        });
           Thread thread2 = new Thread(()->{
               threadLocal.set(2);
               threadLocal.remove();
           });
           thread1.start();
           thread2.start();
        Thread.sleep(5000);

    }
}
