package sp;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {
    }

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums
                .map(a -> a.getName())
                .collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums
                .map(a -> a.getName())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums
                .flatMap(a -> a.getTracks().stream())
                .map(t -> t.getName())
                .sorted()
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> s) {
        return s
                .filter(album -> {
                    for (Track track : album.getTracks()) {
                        if (track.getRating() > 95) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(Comparator.comparing(Album::getName))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(Album::getArtist, Collectors.mapping(Album::getName, Collectors.toList())));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        List<Album> albumsList = albums.collect(Collectors.toList());
        return albumsList.stream().count() - albumsList.stream().distinct().count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        return albums
                .min(Comparator.comparing(a -> {
                    return (a
                            .getTracks()
                            .stream()
                            .max(Comparator.comparing(Track::getRating))
                            .get()
                            .getRating());
                }));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(album -> -album
                .getTracks()
                .stream()
                .mapToInt(Track::getRating)
                .average().getAsDouble()))
                .collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        return stream
                .reduce((x, y) -> ((x * y) % modulo))
                .getAsInt();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {

        return "<" + Arrays.stream(strings)
                .reduce("",
                        (s, s2) -> {
                            if (s == "") {
                                return s2;
                            } else {
                                return s + ", " + s2;
                            }
                        },
                        (s, s2) -> {
                            if (s == "") {
                                return s2;
                            } else {
                                return s + ", " + s2;
                            }
                        }) + ">";
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return s
                .filter(clazz::isInstance)
                .map(object -> (R) object);
    }
}