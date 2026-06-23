
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class User {
    private String name = "Avi";

    public void show() {
        System.out.println("Hello");
    }
}

public class ReflectionEg {
    public static void main(String[] args) {
        Class<?> cls = User.class;
        System.out.println(cls.getName());

        try {
            Method method = cls.getMethod("show");
            method.invoke(new User());

            Field field = cls.getDeclaredField("name");
            field.setAccessible(true);

            System.out.println(field.get(new User()));

            Object obj = cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
