package sp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream()
                .flatMap(s -> {
                    try {
                        return Files.lines(Paths.get(s));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Stream.of();
                    }
                })
                .filter(s -> s.contains(sequence))
                .collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random doubleGenerator = new Random();

        return Stream.generate(() -> doubleGenerator.nextDouble())
                .limit(100000)
                .map(x -> {
                    double y = doubleGenerator.nextDouble();
                    x -= 0.5;
                    y -= 0.5;
                    if (x * x + y * y < 0.25) {
                        return 1;
                    }
                    return 0;
                })
                .mapToInt(Integer::intValue)
                .average()
                .getAsDouble();
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry
                        .getValue()
                        .stream()
                        .mapToInt(String::length)
                        .sum())).get().getKey();

    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders
                .stream()
                .flatMap(map -> map
                        .entrySet()
                        .stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
    }
}