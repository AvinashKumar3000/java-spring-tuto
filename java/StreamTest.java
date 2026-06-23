import java.util.*;
import java.util.stream.*;

public class StreamTest {

    public static void main(String[] args) {

        List<Integer> li = Arrays.asList(5, 2, 8, 2, 10, 15, 20, 8, 25);

        // Full stream operations
        List<Integer> result = li.stream()
                .filter(n -> n % 2 == 0)
                .distinct()
                .map(n -> n * 2)
                .sorted() // ascending order
                .skip(1) // skip first
                .limit(3) // first 3 
                .collect(Collectors.toList());
        System.out.println("Result List: " + result);

        long count = li.stream()
                .filter(n -> n > 5)
                .count();
        System.out.println("Count > 5: " + count);

        int sum = li.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("Sum: " + sum);

        Optional<Integer> first = li.stream()
                .filter(n -> n > 10)
                .findFirst();

        
        System.out.println("First > 10: " + first.orElse(-1));

        boolean hasEven = li.stream()
                .anyMatch(n -> n % 2 == 0);

        System.out.println("Has Even: " + hasEven);

        // allMatch
        boolean allPositive = li.stream()
                .allMatch(n -> n > 0);

        System.out.println("All Positive: " + allPositive);

        // noneMatch
        boolean noneNegative = li.stream()
                .noneMatch(n -> n < 0);
        System.out.println("None Negative: " + noneNegative);

        // forEach
        System.out.println("Printing:");
        li.stream()
                .forEach(System.out::println);
    }
}
