// below is checked exception

import java.util.Arrays;
import java.util.List;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
// below is unchecked exception
class InsufficentBalanceException extends RuntimeException {
    public InsufficentBalanceException(String msg) {
        super(msg);
    }
}
class HandleException {
    double balance = 1000;
    public void vote(int age) throws InvalidAgeException {
        if(age < 18) {
            throw new InvalidAgeException("not eligible for vote.");
        } else{
            System.out.println("thanks for voting.");
        }
    }
    public void withDrawAmount(double amount) {
        if(balance <= 0) {

        }else{
            balance =- amount;
            System.out.println("")
        }
    }
}
public class Application {
    public static void main(String args[]) {
       List<Integer> nums = Arrays.asList(1,2,3,4,5);

        nums.stream()
            .filter(n -> n % 2 == 0)
            .forEach(System.out::println);
    }
}