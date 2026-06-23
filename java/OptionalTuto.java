import java.util.Optional;

public class OptionalTuto {
    public static void main(String[] args) {
        Optional<String> name = Optional.ofNullable("Java");

        name.ifPresent(System.out::println);

        String result = name
                .map(String::toUpperCase)
                .orElse("No Value");

        System.out.println(result);
    }
} 