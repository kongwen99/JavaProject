package combination;

public class Bird {
    private Animal a;

    public Bird(Animal a) {
        this.a = a;
    }

    public void breath() {
        a.breath();
    }

    public void fly() {
        System.out.println("我是一只小小小小鸟，我在天空自由自在的飞呀飞呀飞呀...");
    }
}
