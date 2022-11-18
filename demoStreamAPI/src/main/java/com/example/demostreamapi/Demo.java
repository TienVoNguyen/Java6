package com.example.demostreamapi;

import com.example.demostreamapi.models.Student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    static List<Student> list = Arrays.asList(

            new Student("Nguyén Van Téo", true, 7.5),
            new Student("Tran Thi Thu Huong", false, 5.5),
            new Student("Pham Duc Cudng", true, 9.5),
            new Student("Lé Thi My Héng", false, 6.5),
            new Student("Doan Thi Kim Ty", false, 8.0));


    public static void main(String[] args) {
        demo4();
    }

    private static void demo1() {
        Stream<Integer> stream1 = Stream.of(1, 9, 4, 7, 5, 2);
        stream1.forEach(n -> {
            System.out.println(n);
        });

        List<Integer> list = Arrays.asList(1, 9, 4, 7, 5, 2);
        //Stream<Integer> stream2 = list.stream();
        list.stream().forEach(n -> {
            System.out.println(n);
        });
    }

    private static void demo2() {
        List<Integer> list = Arrays.asList(1, 9, 4, 7, 5, 2);
        List<Double> result = list.stream() // Stream<Integer>
                .filter(n -> n % 2 == 0) // Stream<Integer>
                .map(n -> Math.sqrt(n)) // Stream<Double>
                .peek(d -> System.out.println(d)) // Stream <Double>
                .collect(Collectors.toList()); // List<Double>
    }

    private static void demo3() {
        List<Student> result = list.stream()
                .filter(sv -> sv.getMark() >= 7)
                .peek(sv -> sv.setName(sv.getName().toUpperCase()))
                .collect(Collectors.toList());

        result.forEach(sv -> {
            System.out.println(">> Name: " + sv.getName());
            System.out.println("> > Marks: " + sv.getMark());
            System.out.println();

        });
    }

    private static void demo4() {
        double average = list.stream()
                .mapToDouble(sv -> sv.getMark())
                .average().getAsDouble();
        System.out.println("average: " + average);

        double sum = list.stream()
                .mapToDouble(sv -> sv.getMark())
                .sum();
        System.out.println("sum: " + sum);

        double min_marks = list.stream()
                .mapToDouble(sv -> sv.getMark())
                .min().getAsDouble();
        System.out.println("min_marks: " + min_marks);

        boolean all_passed = list.stream().allMatch (sv -> sv.getMark() >= 5);
        System.out.println("all_passed: " + all_passed);
        Student min_sv = list.stream()
                .reduce(list.get(0), (min, sv) -> sv.getMark() < min.getMark() ? sv: min);
        System.out.println("min_sv: " + min_sv.getName());
    }

}