/**
 * 可变参数：等同于数组又优于数组
 * 1、长度可变的形参只能处于形参列表的最后
 * 2、一个方法中最多智能包含一个长度可变的形参
 * 3、既可以传入多个参数，也可以传入一个数组
 */
public class Varargs {
    //定义了形参个数可变的方法
    public static void test(int a, String... books){
        //books被当做数组进行处理
        for (String book : books){
            System.out.println(book);
        }
        System.out.println(a);
    }

    public static void main(String[] args) {
        test(9, "茶花女", "围城", "了不起的盖茨比");
    }
}
