/**
 * 递归：一个方法体内调用该方法本身，被称为递归
 */
public class Recursive {
    //已知f(20)=1, f(21)=4, f(n+2)=2*f(n+1)+f(n), n>0, 求f(10)
    public static int fn(int n) {
        if (n == 20) {
            return 1;
        }
        else if (n == 21) {
            return 4;
        }
        else {
            return fn(n + 2) - 2 * fn(n + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(fn(10));
    }
}
