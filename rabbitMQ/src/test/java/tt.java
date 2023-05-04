import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author yangz
 * @date 2022/5/19 - 17:19
 */
public class tt {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("124", "225", "754", "225");
        HashSet<String> hashSet = new HashSet<>(list);
        hashSet.forEach(System.out::println);
    }
}
