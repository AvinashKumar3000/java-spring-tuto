import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface EmployeeInfo {
    String name();
    int id();
    String role() default "Developer";
}

@EmployeeInfo(
    name = "Avi",
    id = 101,
    role = "Backend Developer"
)
class Employee {}

public class ImpAnnotation {
    public static void main(String[] args) {

        EmployeeInfo info =
                Employee.class.getAnnotation(EmployeeInfo.class);

        System.out.println(info.name());
        System.out.println(info.id());
        System.out.println(info.role());
    }
}
