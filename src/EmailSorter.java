import java.util.List;
import java.util.ArrayList;

public class EmailSorter {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";// шаблон имейла( 3 символа до @,@,имя домена и зона)

    public static void main(String[] args) {

        String[] emails = { // массив с имейлами
                "abcdefg@gmail.com",
                "a@gmail.com",
                "josh@@gmail.com",
                "janegmail",
                "pete@gmail.com",
                "zoe@gmailcom",
                "steve@outlook.com",
                "abcd@a.a",
                "_-_-_=@gmail.com"
        };

        List<String> valid = new ArrayList<>();// правильно
        List<String> invalid = new ArrayList<>();// не правильно

        for (String email : emails) {
            if (email.matches(EMAIL_REGEX)) {
                valid.add(email);
            } else {
                invalid.add(email);
            }
        }

        System.out.println("right emails:");
        valid.forEach(System.out::println);

        System.out.println("\nwrong emails:");
        invalid.forEach(System.out::println);
    }
}
