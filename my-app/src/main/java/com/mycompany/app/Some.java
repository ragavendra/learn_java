package com.mycompany.app;

import java.io.Console;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*
 * java -d . Some.java
 * java api_test_demo.Some
 */

        enum Colors {
            Cyan, Green, Grey
        }

    enum Day { MODAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

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
        String so = "this is str";

        for (int i = 0; i < someArr_.length; i++) {
            System.out.println("No is " + someArr_[i] + so);
        }


        Colors colors = Colors.Cyan;
        System.out.println(colors.ordinal());

        // System.out.println(cal(Day.FRIDAY));

        List<String> eles = new ArrayList<String>();
        eles.add("one");
        eles.add("three");
        eles.add("123123");

        eles.forEach(n -> { System.out.println(n); });
        // var itr = eles.iterator();
        // itr.next();

        for (String i : retStrLenSix(eles)) {
            System.out.println("Ele is" + i);
        }

        // make any list thrd safe
        List newList = Collections.synchronizedList(eles);
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
 
    /*

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

    int calculateTotalPrice(List<Product> products) {

    int totalPrice = 0;
    Consumer<Product> consumer =
        product -> totalPrice += product.getPrice();
    for (Product product: products) {
        consumer.accept(product);
}
    }*/
    Afce<String> afce = new Afce<String>();
    // afce.PrintType();

}

interface IOtherClass
{
    public String getVarOne();

    public void setVarOne(String str);
}

class OtherClass implements IOtherClass
{
    private String _varOne;

    private String _varTwo;

    public String getVarOne()
    {
        return _varOne;
    }

    public void setVarOne(String str)
    {
        _varOne = str;
    }

    public static void main(String[] args)
    {
        System.out.println("This is OtherClass.");
    }
    
}

interface ISomeOther
{
    public String getSomeVar();
}

class SomeOther implements ISomeOther
{
    protected String _someVar;

    public String getSomeVar()
    {
        return _someVar;
    }
}

class Product extends OtherClass {
    String getPrice(String val)
    {
        setVarOne(val + "1");
        return getVarOne();
    }

    @Override
    @Author(name = "Raga", date = "1/21/2023")
    // @Ebook
    public void setVarOne(String str)
    {
    }
}

class Afce<R>
{
    R _typeName;

    Afce()
    {}

    public void PrintType()
    {
        System.out.println("Type is ");

        if(_typeName instanceof String)
        {
            System.out.println("string");
        }
    }
}

@Documented
@interface Author {
    String name();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    // Note use of array
    // String[] reviewers();
 }

abstract class Product_ implements IOtherClass, ISomeOther
{

}