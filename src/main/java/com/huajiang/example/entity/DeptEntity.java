package com.huajiang.example.entity;

/**
 * @author jianghua
 * @version v1.0
 * @package com.huajiang.example.entity
 * @date 2020/2/9 下午8:19
 * @Copyright
 */
public class DeptEntity {

    //内部类
    public class InnerClass {
    }

    //静态内部类
    public static class StaticInnerClass {
    }


    private int x; //实例变量

    //静态变量：该变量属于该类,类的所有实例都共享静态变量,可以直接通过类名来访问它,静态变量在内存中值存在一份.
    private static int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        DeptEntity.y = y;
    }

    public static void print(){
        System.out.println("this is static method");
    }

    static {
        System.out.println("this is static code block");
    }
}
