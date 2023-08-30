package api_test_some;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*
 * java -d . Some.java
 * java api_test_demo.Some
 */

public class Some
{
    public static void main(String[] args)
    {
        int[] someArr;
        Random rnd = new Random();

        someArr = new int[8];
        // for (int i : someArr) {
        for (int i = 0; i < someArr.length; i++) {
            someArr[i] = rnd.nextInt();
        }

        for (int i = 0; i < someArr.length; i++) {
            System.out.println("No is " + someArr[i]);
        }

        int[] someArr_ = { 21, 33, 19, 39 };
        var so = "this is str";

        for (int i = 0; i < someArr_.length; i++) {
            System.out.println("No is " + someArr_[i] + so);
        }

        enum Colors {
            Cyan, Green, Grey
        }

        Colors colors = Colors.Cyan;
        System.out.println(colors.ordinal());

        System.out.println(cal(Day.FRIDAY));

        List<String> eles = new ArrayList<String>();
        eles.add("one");
        eles.add("three");
        eles.add("123123");

        for (String i : retStrLenSix(eles)) {
            System.out.println("Ele is" + i);
        }
    }

    static List<String> retStrLenSix(List<String> strings)
        {
        // Consumer<String> cons = pro -> colors.ordinal() += 3;
        Predicate<String> pred = s -> s.length() == 3;
        List<String> ls = new ArrayList<>();

        for (String string : strings) {

            if (pred.test(string)) {
                ls.add(string);
            }
        }

        return ls;
    }
 

    enum Day { MODAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

    public static String cal(Day d)
    {
        return switch (d) {
            case SATURDAY, SUNDAY -> "week end";
            default -> {
                int remDays = 6 - d.ordinal();
                yield "weekdays";
            }
        };
    }

    /*
    int calculateTotalPrice(List<Product> products) {

    int totalPrice = 0;
    Consumer<Product> consumer =
        product -> totalPrice += product.getPrice();
    for (Product product: products) {
        consumer.accept(product);
}
    }*/

}

class OtherClass
{
    private String _varOne;

    private String _varTwo;
}

class Product
{
    int getPrice()
    {
        return 1;
    }
}
