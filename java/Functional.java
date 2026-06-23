
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;



@FunctionalInterface
interface Fn {
    int action(int a, int b);
}

class Calc {
    public static void execute(Fn fn, int a, int b) {
        int result = fn.action(a,b);
        System.out.println(result);
    }
}


public class Functional {
    public static void main(String[] args) {
        Fn add = (x,y) -> x+y;
        Fn sub = (x,y) -> x-y;
        Calc.execute(add, 10, 20);
        Calc.execute(sub, 60, 20);

        // -------------
        // Predicate → test
        // Function → transform
        // Consumer → consume
        // Supplier → supply
        Predicate<Integer> isNonZero = a -> a == 0;
        int r1 = isNonZero.test(10) ? 10 : -10;
        System.out.println(r1);

        Function<Integer,Integer> toInt = a -> a + 1;
        int r2 = toInt.apply(100);
        System.out.println(r2);

        Consumer<String> println = (text) -> System.out.println(text);
        println.accept("hello world!");

        Supplier<String> getWarning = () -> "here is your warning!";
        System.out.println(getWarning);
    }
}