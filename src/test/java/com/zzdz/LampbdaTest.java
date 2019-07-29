package com.zzdz;


import com.zzdz.entity.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2019/7/26 12:17
 * @Created by joe
 */
public class LampbdaTest {

    @Test
    public void test1() {
        Consumer<String> consumer = (x) -> System.out.printf(x);
        consumer.accept("hello.");
    }


    /**
     * 一、方法引用：若Lampbda 体中的类容有方法已经实现了，我们可以使用 “方法引用”
     *          可以理解为方法引用是Lampbda表达式的另外一种表现形式。
     *
     * 方法引用三种语法格式：
     *
     * 对象::实例方法名
     *
     * 类::静态方法名
     *
     * 类::实例方法名
     *
     * 使用注意事项：
     * 1.Lampbda 体中调用方法的参数列表于返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致。
     * 2.Lampbda 参数列表中，第一个参数是实例方法的调用者，第二个参数是实例方法的参数时。可以使用。类::实例方法名。
     *
     * 二、构造器引用
     *
     * 语法格式：
     *
     * 类名::new
     *
     * 注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致。
     *
     *
     * 三、数组引用
     *
     * Type::new
     *
     */


    // 对象::实例方法名
    @Test
    public void test2() {
        Consumer<String> consumer = (x) -> System.out.println(x);

        PrintStream out = System.out;
        Consumer<String> consumer1 = out::println;

        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("aaaaaaa");
    }

    @Test
    public void test3() {
        Employee employee = new Employee("joe",33);
        Supplier<String> supplier = () -> employee.getNickName();
        String s = supplier.get();
        System.out.println(s);

        Supplier<Integer> supplier1 = employee::getAge;
        System.out.println(supplier1.get());
    }


    // 类::静态方法名
    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x,y);

        Comparator<Integer> comparator1 = Integer::compare;
    }

    // 类::实例方法名
    @Test
    public void test5() {
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);

        BiPredicate<String, String> biPredicate1 = String::equals;
    }

    // 构造器引用
    @Test
    public void test6() {
        Supplier<Employee> supplier = () -> new Employee();
        Supplier<Employee> supplier1 = Employee::new;

        Function<String,Employee> function = (x) -> new Employee(x);
        Function<String,Employee> function1 = Employee::new;
        System.out.println(function1.apply("haha"));

        BiFunction<String,Integer,Employee> function2 = Employee::new;
        System.out.println(function2.apply("joe",34));
    }

    // 数组引用
    @Test
    public void test7() {
        Function<Integer, String[]> function = (x) -> new String[x];
        System.out.println(function.apply(10).length);

        Function<Integer, String[]> function1 = String[]::new;
        System.out.println(function1.apply(20).length);
    }

}