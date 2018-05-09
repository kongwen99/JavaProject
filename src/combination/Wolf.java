package combination;

public class Wolf {
    private Animal a;

    public Wolf(Animal a) {
        this.a = a;
    }

    public void breath(){
        a.breath();
    }
    public void run(){
        System.out.println("我是一只来自北方的狼，我在草原上跑啊跑啊跑啊。。。");
    }
}
