import streams.People;
import streams.Sex;

import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Collection<People> peoples = Arrays.asList(
                new People("Вася", 16, Sex.MAN),
                new People("Петя", 23, Sex.MAN),
                new People("Елена", 42, Sex.WOMEN),
                new People("Иван Иванович", 69, Sex.MAN)
        );
        System.out.println("Исходная выборка:");
        peoples.forEach(System.out::println);

        System.out.println("\nМужчины-военнообязанные:");
        peoples.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .filter(x -> (x.getAge() >= 18 && x.getAge() < 66))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        /*В данном случае результат терминальной операции будет OptionalDouble, поэтому
        getAsDouble() в случае отсутствия в исходной выборке мужчин выдаст исключение  java.util.NoSuchElementException
        System.out.printf("\nСредний возраст мужчин: %s лет\n",
                peoples.stream()
                        .filter(x -> x.getSex() == Sex.MAN)
                        .mapToInt(x -> x.getAge())
                        .average().getAsDouble());*/

        /*Решить проблему можно так:
        OptionalDouble averageMan = peoples.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .mapToInt(x -> x.getAge())
                .average();

        if (averageMan.isPresent()) {
            System.out.printf("\nСредний возраст мужчин: %s лет\n", averageMan.getAsDouble());
        } else {
            System.out.println("\nВ исходной выборке мужчины не представлены");
        }*/

        //или так:
        peoples.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .mapToInt(x -> x.getAge())
                .average()
                .ifPresentOrElse(
                        v -> System.out.printf("\nСредний возраст мужчин: %s лет\n", v),
                        () -> System.out.println("\nВ исходной выборке мужчины не представлены")
                );
        //или просто сделать обработку исключения


        System.out.println("\nПотенциально работоспособные люди:");
        peoples.stream()
                .filter(x -> (x.getAge() >= 18 && (x.getAge() < 66 && x.getSex() == Sex.MAN ||
                        x.getAge() < 61 && x.getSex() == Sex.WOMEN)))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
