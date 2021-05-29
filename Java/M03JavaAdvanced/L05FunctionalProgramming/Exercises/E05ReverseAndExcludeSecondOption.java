package bg.softuni.java_advanced.functional_programming.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class E05ReverseAndExcludeSecondOption {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        String input = reader.readLine();

        int divisor = Integer.parseInt(reader.readLine());

        Predicate<Integer> isDivisible = getDivisible(divisor);

        List<Integer> numbers = getList(input);

        numbers.removeIf(isDivisible);

        Collections.reverse(numbers);

        Consumer<List<Integer>> printer = getPrinter();

        printer.accept(numbers);
    }

    private static List<Integer> getList(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static Predicate<Integer> getDivisible(int divisor) {
        return number -> number % divisor == 0;
    }

    private static Consumer<List<Integer>> getPrinter() {
        return list -> {
            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
        };
    }
}
