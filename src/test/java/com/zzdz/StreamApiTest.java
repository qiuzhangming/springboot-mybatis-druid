package com.zzdz;

import com.zzdz.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.zzdz.entity.Employee.Status.*;

/**
 * @Classname StreamApiTest
 * @Description TODO
 * @Date 2019/7/29 12:19
 * @Created by joe
 */
public class StreamApiTest {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 19, 9999.99, FREE),
            new Employee("李四", 28, 2222.22, BUSY),
            new Employee("王五", 36, 6666.66, VOCATION),
            new Employee("赵六", 47, 7777.77, BUSY),
            new Employee("田七", 51, 5555.55, FREE),
            new Employee("田七", 51, 5555.55, FREE),
            new Employee("田七", 51, 5555.55, FREE)
    );

    /**
     * Stream 三个操作步骤
     * 1.创建 Stream
     * 2.中间操作
     * 3.终止操作（终端操作）
     */

    // 创建 Stream
    @Test
    public void test1() {
        //1.可以通过Collection 系列集合提供的stream() 或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2.通过Arrays 中的静态方法stream获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);

        //3.通过 Stream 类中的静态方法of()
        Stream<String> stream2 = Stream.of("aa", "bb", "cc");

        //4.创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2);
        //stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(() -> Math.random());
        stream4.forEach(System.out::println);
    }

    //中间操作

    /**
     * 筛选于切片
     * filter：接收Lampbda，从流中排除某些元素。
     * limit： 截断流，使其元素不超过给定数量。
     * skip(n)：跳过元素，返回一个扔掉了前n个元素的流。若流中的元素不足n个，则返回一个空流。于limit(n) 互补。
     * distinct：筛选，通过流所生成元素的hashCode()和equals()去除重复元素。
     */

    //内部迭代：迭代操作由Stream API 完成
    @Test
    public void test2() {
        //中间操作: 不会执行任何操作
        Stream<Employee> stream = employees.stream()
                .filter((e) -> e.getAge() > 35);

        // 终止操作：一次性执行全部内容，即“惰性求值”
        stream.forEach(System.out::println);
    }

    @Test
    public void test3() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路！");
                    return e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }


    @Test
    public void test4() {
        employees.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map：接收Lampbda，将元素转换为其他形式或拉取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成为一个新的元素。
     * flatMap：接收一个函数作为参数，将流中的每一个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test5() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);
        System.out.println("---------------------------------------");

        // Employee::getNickName 的理解。
        //Function<Employee, String> function = (e) -> e.getNickName();
        //System.out.println(function.apply(new Employee("张三",19,9999.99)));

        employees.stream()
                .map(Employee::getNickName)
                .distinct()
                .forEach(System.out::println);

        System.out.println("---------------------------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(StreamApiTest::filterCharcter);// {{a,a,a},{b,b,b},{c,c,c}}
        stream.forEach((s) -> s.forEach(System.out::print));
        System.out.println();

        System.out.println("---------------------------------------");
        Stream<Character> stream1 = list.stream()
                .flatMap(StreamApiTest::filterCharcter);//{a,a,a,b,b,b,c,c,c}
        stream1.forEach(System.out::print);
        System.out.println();

    }

    public static Stream<Character> filterCharcter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     * sorted():自然排序（Comparable）
     * sorted(Comparator com):定制排序（Comparator）
     */
    @Test
    public void test6() {
        List<String> list = Arrays.asList("ccc", "bbb", "aaa", "ddd", "eee");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("---------------------------------------");

        employees.stream().sorted((e1, e2) -> {
            if (e1.getAge().equals(e2.getAge())) {
                return e1.getNickName().compareTo(e2.getNickName());
            } else {
                return -e1.getAge().compareTo(e2.getAge());
            }
        }).forEach(System.out::println);
    }


    // 终止操作

    /**
     * 查找与匹配
     * allMatch：检查是否匹配所有元素
     * anyMatch：检查是否匹配一个元素
     * noneMatch：检查是否没有匹配所有元素
     * findFrist：返回第一个元素
     * findAny：返回当前流中的任意元素
     * count：返回流中元素的总个数
     * max：返回流中最大值
     * min：返回流中最小值
     */

    @Test
    public void test7() {
        boolean b = employees.stream().allMatch((e) -> e.getStatus().equals(BUSY));
        System.out.println(b);

        boolean b1 = employees.stream().anyMatch((e) -> e.getStatus().equals(BUSY));
        System.out.println(b1);

        boolean b2 = employees.stream().noneMatch((e) -> e.getStatus().equals(BUSY));
        System.out.println(b2);

        Optional<Employee> employeeOptional = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary)) //(e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())
                .findFirst();
        System.out.println(employeeOptional.get());


        Optional<Employee> any = employees.parallelStream()
                .filter((e) -> e.getStatus().equals(FREE))
                .findAny();
        System.out.println(any.get());
    }

    @Test
    public void test8() {
        long count = employees.stream().count();
        System.out.println(count);

        Optional<Employee> max = employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
        System.out.println(max.get());

        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(min.get());
    }

    /**
     * 规约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)：可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test9() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        Optional<Integer> reduce1 = list.stream()
                .reduce((x, y) -> x + y);
        System.out.println(reduce1.get());

        Optional<Double> reduce2 = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(reduce2.get());
    }

    /**
     * 收集
     * collect：将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test10() {
        List<String> collect = employees.stream()
                .map(Employee::getNickName)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("-------------------------------------");

        Set<String> collect1 = employees.stream()
                .map(Employee::getNickName)
                .collect(Collectors.toSet());
        collect1.forEach(System.out::println);
        System.out.println("-------------------------------------");

        HashSet<String> collect2 = employees.stream()
                .map(Employee::getNickName)
                .collect(Collectors.toCollection(HashSet::new));
        collect2.forEach(System.out::println);

    }

    @Test
    public void test11() {
        // 总数
        Long collect = employees.stream()
                .collect(Collectors.counting());
        System.out.println(collect);
        System.out.println("-------------------------------------");

        // 评价值
        Double collect1 = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect1);

        // 总和
        Double collect2 = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(collect2);

        // 最大值
        Optional<Employee> max = employees.stream().max(Comparator.comparingDouble(Employee::getSalary));
    }

    // 分组
    @Test
    public void test12() {
        Map<Employee.Status, List<Employee>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect);
    }
    //多级分组
    @Test
    public void test13() {
        Map<Employee.Status, Map<String, List<Employee>>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e1) -> {
                    if (e1.getAge() > 35) {
                        return "老年";
                    } else {
                        return "青年";
                    }
                })));
        System.out.println(collect);
    }


    // 分区
    @Test
    public void test14() {

        Map<Boolean, List<Employee>> collect = employees.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 3000));

        System.out.println(collect);
    }

    @Test
    public void test15() {
        DoubleSummaryStatistics collect = employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect.getMax());
        System.out.println(collect.getAverage());
        System.out.println(collect.getSum());
    }

    @Test
    public void test16() {
        String collect = employees.stream()
                .map(Employee::getNickName)
                .collect(Collectors.joining(","));
        System.out.println(collect);
    }

}
