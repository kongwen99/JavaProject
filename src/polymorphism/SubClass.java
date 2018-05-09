package polymorphism;

public class SubClass extends BaseClass{
    public String book = "成为一个合格的程序员，掌握基础很重要！";

    public void test() {
        System.out.println("子类覆盖父类的方法");
    }

    public void sub() {
        System.out.println("子类的普通方法");
    }

    public static void main(String[] args) {
        //下面这句代码，编译时类型是BaseClass，运行时类型是SubClass
        BaseClass bc = new SubClass();
        System.out.println(bc.book);
        bc.base();
        bc.test();
        ((SubClass) bc).sub();
    }
}
