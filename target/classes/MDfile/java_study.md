#同步和互斥
java提供了两种锁机制来控制多个线程对共享资源的互斥访问,  
一个是jvm实现的synchronized,一个是jdk实现的reentrantlock  
###synchronized:  
<u>它对应的内存间交互操作为</u>：lock 和 unlock，在虚拟机实现上对应的字节码指令为 monitorenter 和 monitorexit。  
1. 同步一个代码块，他只作用与同一个对象，如果调用两个不同的对象的同步代码，则不会同步。  
2. 同步一个方法，与同步一个代码块一样。  
3. 同步一个类，作用于整个类，该类所有对象都会进行同步.  
###sun.msic.unsafe
该类为我们提供了类似c++手动管理内存的能力。即可以通过这个类直接操作指定的内存区域。该类是final的无法继承，且构造函数是私有的即无法获取对象。如果要使用该类可以通过反射机制获取。  
###java中的原子操作
java.util.concurrent.atomic 包下定义了很多原子类型，这些类中包含了对该类型对象的原子操作。  
以AtomicInteger类为例：  
AtomicInteger中只有一个成员变量value，且它为volatile从而实现多线程并发时的可见性。而对value的各种操作实际是通过Unsafe类的CAS（compareAndSet(expectValue，updateValue)方法）最终实现，  
CAS原理：CAS一般应用于乐观锁机制比如：ConcurrentHashMap，ConcurrentLinkedQueue都有用到CAS来实现乐观锁，  
通过比较expectValue 和内存中的值，如果相等则将updateValue更新进内存，而如果不等则直接返回。
###reentrantlock:
类似于synchronized的同步代码块。  
###选择：
一般情况下优先选择synchronized,除非要实现高级功能，因为它是jvm实现的一种机制，jvm原生的支持它，且不用担心没有释放锁而导致的死锁问题  
###线程之间协作：
在线程中调用另一个线程的join()方法，当前方法就会别挂起，而不是忙等，直到目标线程执行完毕。  
调用wait() 会使当前进程进入等待，线程在等待时会被挂起，当其他线程运行使得条件满足时，其他线程调用notify()和notifyall()方法唤醒等待进程。  
它们都属于Object的一部分，而不属于Thread。  
只能用在同步方法或同步控制块中，否则会抛出IllegalMonitorException  
使用wait()挂起期间，线程会释放锁，这是因为，如果没有释放锁，那么其它线程就无法进入对象的同步方法或者同步控制块中，那么就无法执行 notify() 或者 notifyAll() 来唤醒挂起的线程，造成死锁。  
####wait()与sleep()区别:
* wait()是Object的方法，sleep()是Thread的方法  
* wait()会释放锁，sleep()不会
###await(),signal(),signalall():
java.util.concurrent 类库中提供了 Condition 类来实现线程之间的协调，可以在 Condition 上调用 await() 方法使线程等待，其它线程调用 signal() 或 signalAll() 方法唤醒等待的线程。  
##线程状态：
* 新建  
* 可运行 Runnable  
* 阻塞 block  
* 无限期等待  
* 限期等待  
* 死亡  
##J.U.C-AQS
java.util.concurrent  大大提高了并发性能，AQS是J.U.C的核心  
###CountDownLatch
CountDownLatch计数器闭锁是一个能阻塞当前线程，让其他线程满足特定条件下当前线程再继续执行的<u>线程同步工具</u>。  

在单独主线程的情况下,创建多个子线程, 在保证当前线程同步的情况下,同时又让多个子线程处理,可以使用 CountDownLatch来阻塞主线程,等待子线程执行完成,主线程才执行后续操作.  
###CyclicBarrier
循环栅栏 :用来控制多个线程互相等待，只有当多个线程都到达时，<u>这些线程</u>才会继续执行，也是<u>线程同步工具</u>。  
CyclicBarrier 和 CountdownLatch 的一个区别是，CyclicBarrier 的计数器通过调用 reset() 方法可以循环使用，所以它才叫做循环屏障。  
###Semaphore
Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。  
###FutureTask
FutureTask 实现了 RunnableFuture 接口，该接口继承自 Runnable 和 Future 接口，这使得 FutureTask 既可以当做一个任务执行，也可以有返回值  
FutureTask 可用于异步获取执行结果或取消执行任务的场景。当一个计算任务需要执行很长时间，那么就可以用 FutureTask 来封装这个任务，主线程在完成自己的任务之后再去获取结果  
###BlockingQueue
java.util.concurrent.BlockingQueue 接口有以下阻塞队列的实现：  
* FIFO 队列 ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）  
* 优先级队列 ：PriorityBlockingQueue  

提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。  
可用于实现生产者消费者问题  
###ForkJoin
主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算。  
ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数。  
##java内存模型
* 主内存:实际的物理内存  
* 工作内存:存储在高速缓存或者寄存器中  
所有变量都存储在主内存中，每个线程有自己的工作内存，该工作内存中存储有该线程使用的变量的副本，线程只能操作其工作内存中的变量，  
不同内存间的值传递需要通过主内存来实现  
###内存间的交互操作
* read：把一个变量的值从主内存传输到工作内存中  
*    load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中  
*   use：把工作内存中一个变量的值传递给执行引擎  
*   assign：把一个从执行引擎接收到的值赋给工作内存的变量  
*   store：把工作内存的一个变量的值传送到主内存中  
*  write：在 store 之后执行，把 store 得到的值放入主内存的变量中  
*   lock：作用于主内存的变量  
*   unlock  
###内存模型三大特性
1. 原子性  
2. 可见性：  
可见性指当一个线程修改了共享变量的值，其它线程能够立即得知这个修改。Java 内存模型是通过在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值来实现可见性的。

主要有三种实现可见性的方式：
* volatile  易变的，无定性的
* synchronized，对一个变量执行 unlock 操作之前，必须把变量值同步回主内存。  
* final，被 final 关键字修饰的字段在构造器中一旦初始化完成，并且没有发生 this 逃逸（其它线程通过 this 引用访问到初始化了一半的对象），那么其它线程就能看见 final 字段的值。  
3. 有序性  
可以通过 volatile 和synchronized 来实现有序性  
##线程安全
多个线程不管以何种方式访问某个类，并且在主调代码中不需要进行同步，都能表现正确的行为。  
线程安全有以下几种实现方式：
###不可变
不可变的类型：  
* final 关键字修饰的基本数据类型
* String
* 枚举类型
* Number 部分子类，如 Long 和 Double 等数值包装类型，BigInteger 和 BigDecimal 等大数据类型。但同为 Number 的原子类 AtomicInteger 和 AtomicLong 则是可变的。  
对于集合类型，可以使用 Collections.unmodifiableXXX() 方法来获取一个不可变的集合。  
###互斥同步
synchronized 和 ReentrantLock。属于是阻塞同步  
互斥同步最主要的问题就是线程阻塞和唤醒所带来的性能问题，因此这种同步也称为阻塞同步。  
互斥同步属于一种悲观的并发策略，总是认为只要不去做正确的同步措施，那就肯定会出现问题。无论共享数据是否真的会出现竞争，它都要进行加锁（这里讨论的是概念模型，实际上虚拟机会优化掉很大一部分不必要的加锁）、用户态核心态转换、维护锁计数器和检查是否有被阻塞的线程需要唤醒等操作。
###非阻塞同步
随着硬件指令集的发展，我们可以使用基于冲突检测的乐观并发策略：先进行操作，如果没有其它线程争用共享数据，那操作就成功了，否则采取补偿措施（不断地重试，直到成功为止）。这种乐观的并发策略的许多实现都不需要将线程阻塞，因此这种同步操作称为非阻塞同步。  
1. CAS  
2. AtomicInteger  
3. ABA  
### 无同步方案
1. 栈封闭  
多个线程访问同一个方法的局部变量时，不会出现线程安全问题，因为局部变量存储在虚拟机栈中，属于线程私有的。  
2. ThreadLocal  
线程自己拥有ThreadLocal的副本，自己玩耍，不影响其他的线程。  
ThreadLocal 从理论上讲并不是用来解决多线程并发问题的，因为根本不存在多线程竞争。  
在一些场景 (尤其是使用线程池) 下，由于 ThreadLocal.ThreadLocalMap 的底层数据结构导致 ThreadLocal 有内存泄漏的情况，应该尽可能在每次使用 ThreadLocal 后手动调用 remove()，以避免出现 ThreadLocal 经典的内存泄漏甚至是造成自身业务混乱的风险。  
3. 可重入代码  
这种代码也叫做纯代码（Pure Code），可以在代码执行的任何时刻中断它，转而去执行另外一段代码（包括递归调用它本身），而在控制权返回后，原来的程序不会出现任何错误  
##多线程开发良好的实践
* 给线程起个有意义的名字，这样可以方便找 Bug。

* 缩小同步范围，从而减少锁争用。例如对于 synchronized，应该尽量使用同步块而不是同步方法。

* 多用同步工具少用 wait() 和 notify()。首先，CountDownLatch, CyclicBarrier, Semaphore 和 Exchanger 这些同步类简化了编码操作，而用 wait() 和 notify() 很难实现复杂控制流；其次，这些同步类是由最好的企业编写和维护，在后续的 JDK 中还会不断优化和完善。

* 使用 BlockingQueue 实现生产者消费者问题。

* 多用并发集合少用同步集合，例如应该使用 ConcurrentHashMap 而不是 Hashtable。

* 使用本地变量和不可变类来保证线程安全。

* 使用线程池而不是直接创建线程，这是因为创建线程代价很高，线程池可以有效地利用有限的线程来启动任务。  
#JavaIO
* 磁盘操作：File 类可以用作表示文件和目录信息， 从java7开始，可以使用Paths和Files代替File  
* 字节操作：InputStream 和 OutputStream  
* 字符操作：Reader 和 Writer  
* 对象操作：Serializable
* 网络操作：Socket  
* 新的输入输出：NIO  
###装饰者模式
Java I/O使用了装饰者模式，来实现。以InputStream为例，  
* InputStream 是抽象组件，抽象类；
* FileInputStream 是 InputStream 的子类，属于具体组件，提供了字节流的输入操作；
* FilterInputStream 属于抽象装饰者，装饰者用于装饰组件，为组件提供额外的功能。例如 BufferedInputStream 为 FileInputStream 提供缓存的功能。  
##Reader,Writer
不管是磁盘还是网络传输，最小的存储单元都是字节，而不是字符。但是在程序中操作<u>比如System.out.println(line);</u>的通常是字符形式的数据，因此需要提供对字符进行操作的方法。  
* InputStreamReader 实现从字节流解码成字符流；
* OutputStreamWriter 实现字符流编码成为字节流。  
##对象操作
###序列化
序列化就是将一个对象转换成字节序列，方便存储和传输。  
* 序列化：ObjectOutputStream.writeObject()
* 反序列化：ObjectInputStream.readObject()  
####Serializable
序列化的类需要实现 Serializable 接口，它只是一个标准，没有任何方法需要实现，但是如果不去实现它的话而进行序列化，会抛出异常  
####transient
transient 关键字可以使一些属性不会被序列化。  
ArrayList 中存储数据的数组 elementData 是用 transient 修饰的，因为这个数组是动态扩展的，并不是所有的空间都被使用，因此就不需要所有的内容都被序列化。通过重写序列化和反序列化方法，使得可以只序列化数组中有内容的那部分数据
##网络操作
java 中的网络支持  
* InetAddress：用于表示网络上的硬件资源，即 IP 地址；  
* URL：统一资源定位符； 可以直接从 URL 中读取字节流数据  
* Sockets：使用 TCP 协议实现网络通信；  
* Datagram：使用 UDP 协议实现网络通信  
#NIO
##流与块
I/O 与 NIO 最重要的区别是数据打包和传输的方式，I/O 以流的方式处理数据，而 NIO 以块的方式处理数据。  
面向流的 I/O 一次处理一个字节数据：一个输入流产生一个字节数据，一个输出流消费一个字节数据。为流式数据创建过滤器非常容易，链接几个过滤器，以便每个过滤器只负责复杂处理机制的一部分。不利的一面是，面向流的 I/O 通常相当慢。  
面向块的 I/O 一次处理一个数据块，按块处理数据比按流处理数据要快得多。但是面向块的 I/O 缺少一些面向流的 I/O 所具有的优雅性和简单性。  
I/O 包和 NIO 已经很好地集成了，java.io.* 已经以 NIO 为基础重新实现了，所以现在它可以利用 NIO 的一些特性。例如，java.io.* 包中的一些类包含以块的形式读写数据的方法，这使得即使在面向流的系统中，处理速度也会更快。
##通道与缓冲区
1. 通道  
通道 Channel 是对原 I/O 包中的流的模拟，可以通过它读取和写入数据。  
通道与流的不同之处在于，流只能在一个方向上移动(一个流必须是 InputStream 或者 OutputStream 的子类)，而通道是双向的，可以用于读、写或者同时用于读写。  
通道包括以下类型：  
* FileChannel：从文件中读写数据；  
* DatagramChannel：通过 UDP 读写网络中数据；  
* SocketChannel：通过 TCP 读写网络中数据；  
* ServerSocketChannel：可以监听新进来的 TCP 连接，对每一个新进来的连接都会创建一个 SocketChannel。  
2. 缓冲区
发送给一个通道的所有数据都必须首先放到缓冲区中，同样地，从通道中读取的任何数据都要先读到缓冲区中。也就是说，不会直接对通道进行读写数据，而是要先经过缓冲区。  
缓冲区实质上是一个数组，但它不仅仅是一个数组。缓冲区提供了对数据的结构化访问，而且还可以跟踪系统的读/写进程。  
缓冲区包括以下类型：  
* ByteBuffer  
* CharBuffer  
* ShortBuffer  
* IntBuffer  
* LongBuffer  
* FloatBuffer  
* DoubleBuffer  
缓冲区状态变量：  
* capacity：最大容量；  
* position：当前已经读写的字节数；  
* limit：还可以读写的字节数。  
##对比
NIO 与普通 I/O 的区别主要有以下两点：  
* NIO 是非阻塞的；  
* NIO 面向块，I/O 面向流。  
##java反射机制
java反射机制，简单的说，反射机制的是程序运行时能够获取自身的信息。 在java中，只要给定类的名字，那么就可以通过反射机制来获得类的信息。  
使用实例：Class.forName("com.mysql.jdbc.Driver.class").newInstance();  
能干什么：  
反射机制能够帮助我们做那些重复的事情，例如现在很多的自动生成代码的软件就是运用反射机制来实现的，  
更常见的是现在的web框架，比如spring，配置bean信息，@Bean等。都是使用反射机制将它们放入容器中。  
##java动态代理
使用代理可以在不直接侵入原代码的情况下，向目标类的目标方法织入我们想要的操作。比如在执行某个方法前后织入日志。  
首先，静态代理：   
![staticProxy](/pics/StaticProxy.png)
静态代理就是要代理类与目标类实现同一个接口，然后使用代理类来完成与目标类相同的功能。实际就是在代理类里面创建一个目标类对象，然后再调用目标类的方法。  
缺点：很明显，要为每个目标类创建一个代理类。  
动态代理：  
jdk为我们提供了java.lang.reflect.InvocationHandler接口和java.lang.reflect.Proxy类，两者相互配合即可实现动态代理。  
动态代理类：在程序运行是通过反射机制动态生成。  
动态代理类通常代理接口下的所有类。  






