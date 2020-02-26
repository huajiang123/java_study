import com.huajiang.example.entity.UserEntity;
import com.huajiang.example.jvm.Car;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/23 下午7:12
 * @Copyright
 */
public class JVMTest {

    @Test
    public void test_01(){
        Car car = new Car("xxxxx");
        Class<Car> class1 = (Class<Car>) car.getClass();
        System.out.println(class1);
        ClassLoader classLoader = class1.getClassLoader();
        System.out.println(classLoader);
    }

    @Test
    public void test_02(){
        //返回虚拟机试图使用的最大内存
        long max = Runtime.getRuntime().maxMemory();
        //返回jvm的初始化总内存
        long total = Runtime.getRuntime().totalMemory();
        System.out.println("max: "+ max);
        System.out.println("total: "+ total);

        //-Xms68m -Xmx68m -XX:+PrintGCDetails
        //默认情况下，分配的总内存是电脑内存的1/4,而初始化的内存：1/64
    }

    @Test
    public void test_03(){
        String str = "xxxxxxx";
        while (true){
            str += str+new Random().nextInt(8888888)+new Random().nextInt(9999999);
        }
    }

    @Test
    public void test_04(){
        String s0 = "abcd";
        String s1 = new String("abcd");
        String s2 = s1.intern();
        System.out.println(s2);
        System.out.println(s1 == s2);//false 一个对象在堆中s1// ，一个对象在常量池中s2。
    }

    @Test
    public void test_05(){
        String s1 = "str";
        String s2 = "ing";
        String s3 = s1 + s2;
        String s4 = "str" + "ing";
        String s5 = "string";
        System.out.println(s3 == s4);//false 一个在堆中，一个在常量池中
        System.out.println(s3 == s5);//false 一个在堆中，一个在常量池中
        System.out.println(s4 == s5);//true 都在常量池中
    }

    @Test
    public void test_06(){
        Integer integer1 = 40;
        Integer integer2 = 40;
        Integer integer3 = 0;
        Integer integer4 = new Integer(40);
        Integer integer5 = new Integer(40);
        Integer integer6 = new Integer(0);
        System.out.println(integer1 == integer2);//true
        System.out.println(integer1 == integer2 + integer3);//true
        System.out.println(integer1 == integer4);//false 一个在堆中，一个在常量池中
        System.out.println(integer4 == integer5);//false 堆中两个不同的对象
        System.out.println(integer4 == integer5 + integer6);//true 先进行加法运算，先拆箱，再运算，然后比较，比较的时候integer4也要先拆箱，再比较。
        System.out.println(40 == integer4 + integer6);//true 因为会先计算加法，而Integer没有+运算，所以只能进行拆箱操作，然后计算，最后再比较。
    }

    @Test
    public  void test_07(){

        UserEntity userEntity = new UserEntity();
        WeakReference weakReference = new WeakReference(userEntity);
        System.gc();
        System.out.println(weakReference.get());
    }
}
