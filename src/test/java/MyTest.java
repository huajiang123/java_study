import com.huajiang.example.entity.DeptEntity;
import com.huajiang.example.entity.EqualExample;
import com.huajiang.example.entity.UserEntity;
import org.junit.Test;

import java.util.*;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/9 下午6:21
 * @Copyright
 */

public class MyTest {
    @Test
    public void test_01(){

        HashMap hashMap = new HashMap();
        hashMap.entrySet().iterator();
        Integer x = new Integer(123);
        Integer y = new Integer(123);
        System.out.println(x==y); //false
        Integer z = Integer.valueOf(123);
        Integer k = Integer.valueOf(123);
        System.out.println(z==k); //true
        Integer m = 123;
        Integer n = 123;
        System.out.println(m==n); //true
        String a = "aaa";
        String b = "aaa";
        //true java中字符串常量是共享的，只有一个拷贝，如果是采用 "bbb" 这种字面量的形式创建字符串，
        // 会自动地将字符串放入 String Pool 中
        System.out.println(a==b);
    }

    public void test_02(){
        //字面量是double,会发生精度丢失
        //float f = 1.1;
        float f = 1.1f;

    }

    public void test_03(){
        final int x = 1;
        //x = 2; 不可修改final修饰的变量
        //对于引用类型，final 使引用不变，也就不能引用其它对象，但是被引用的对象本身是可以修改的
        final UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        DeptEntity.print();
    }

    public void test_04(){
        DeptEntity deptEntity = new DeptEntity();
        //非静态内部类不能直接实例化
        DeptEntity.InnerClass innerClass = deptEntity.new InnerClass();
        //静态内部类可以直接实例化
        DeptEntity.StaticInnerClass staticInnerClass = new DeptEntity.StaticInnerClass();
    }

    @Test
    public void test_05(){
        Integer x = new Integer(1);
        Integer y = new Integer(1);
        DeptEntity deptEntity = new DeptEntity();
        DeptEntity dept = new DeptEntity();
        System.out.println(dept.equals(deptEntity));
        System.out.println(x.equals(y)); //就是比较value
        System.out.println(x == y);
    }

    @Test
    public void test_06(){
        EqualExample e1 = new EqualExample(1,1,1);
        EqualExample e2 = new EqualExample(1,1,1);
        System.out.println(e1.equals(e2)); //true
        HashSet<EqualExample> set = new HashSet<EqualExample>();
        set.add(e1);
        set.add(e2);//两个对象等价，因此两个对象的hash值一样，因此集合中只有值
        System.out.println(set.size());//1
    }

    /**
     * TreeSet：基于红黑树实现，支持有序性操作，例如根据一个范围查找元素的操作。
     * 但是查找效率不如 HashSet，HashSet 查找的时间复杂度为 O(1)，TreeSet 则为 O(logN)。
     */
    @Test
    public void test_07(){
        TreeSet<String> set = new TreeSet<String>();
        set.add("aaa");
        set.add("bbb");
        set.add("aaa");
        set.add("ccc");
        set.add("eee");
        set.add("bbb");
        set.add("ddd");
        set.add("ccc");
        //顺序遍历
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println("asc: "+iterator.next());
        }

        //逆序遍历
        Iterator descendingIterator = set.descendingIterator();
        while (descendingIterator.hasNext()){
            System.out.println("desc: "+ descendingIterator.next());
        }

        //元素逆置
        NavigableSet<String> treeSet = set.descendingSet();
        Iterator it = treeSet.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    /**
     * HashSet 基于哈希表实现，支持快速查找，但不支持有序性操作。并且失去了元素的插入顺序信息，
     * 也就是说使用 Iterator 遍历 HashSet 得到的结果是不确定的
     */
    @Test
    public void test_08(){
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("aaa");
        hashSet.add("aaa");
        hashSet.add("bbb");
        hashSet.add("xxx");
        hashSet.add("bbb");
        hashSet.add("ccc");
        hashSet.add("ddd");
        hashSet.add("rrr");
        hashSet.add("ccc");
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        Iterator iterator1 = hashSet.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
    }

    /**
     * LinkedHashSet：具有 HashSet 的查找效率，并且内部使用双向链表维护元素的插入顺序。
     */
    @Test
    public void test_09(){
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        linkedHashSet.add("aaa");
        linkedHashSet.add("bbb");
        linkedHashSet.add("ccc");
        linkedHashSet.add("aaa");
        linkedHashSet.add("ccc");
        linkedHashSet.add("xxx");
        linkedHashSet.add("rrr");
        linkedHashSet.add("rrr");
        Iterator iterator = linkedHashSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /**
     *

     ArrayList：基于动态数组实现，支持随机访问。

     Vector：和 ArrayList 类似，但它是线程安全的。

     LinkedList：基于双向链表实现，只能顺序访问，但是可以快速地在链表中间插入和删除元素。不仅如此，LinkedList 还可以用作栈、队列和双向队列。

     */
    @Test
    public void test_10(){
        List<String> list = new LinkedList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add(2,"ddd"); //从0开始计数
        list.add("aaa");
        list.add("eee");
        list.add(1,"fff");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /**
     * TreeMap：基于红黑树实现。
     * HashTable：和 HashMap 类似，但它是线程安
     * 全的，这意味着同一时刻多个线程同时写入 HashTable 不会导致数据不一致。
     * 它是遗留类，不应该去使用它，而是使用 ConcurrentHashMap 来支持线程安全，
     * ConcurrentHashMap 的效率会更高，因为 ConcurrentHashMap 引入了分段锁。
     * LinkedHashMap：使用双向链表来维护元素的顺序，顺序为插入顺序或者最近最少使用（LRU）顺序。
     */
    @Test
    public void test_11(){
        TreeMap<String,String> treeMap = new TreeMap<String, String>();
        treeMap.put("aaa","I AM AAA");
        treeMap.put("bbb","I AM BBB");
        treeMap.put("ccc","I AM CCC");
        treeMap.put("aaa","I AM A");
        treeMap.put("xxx","I AM XXX");
        treeMap.put("ddd","I AM DDD");
        treeMap.put("eee","I AM EEE");
        Set set= treeMap.keySet();
       Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public  void test_12(){
        int origin = 12;
        int after = origin >> 1;//减半
        System.out.println(after);
    }

    @Test
    public void test_13(){
        List<String> userNames = new ArrayList<String>() {{
            add("Jobs"); add("jobs"); add("JobsSteven"); add("J"); }};
        for (String userName : userNames) {
            if (userName.equals("Jobs")) {
                userNames.remove(userName);
            }
        }
        System.out.println(userNames);
    }


}
