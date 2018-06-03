package util;

import report.WorkerReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Operations {
    static Map<String, Function<Stream<WorkerReport>, Number>> availableOperations = createMap();

    private static Map<String, Function<Stream<WorkerReport>, Number>> createMap() {
        Map<String, Function<Stream<WorkerReport>, Number>> myMap = new HashMap<>();
        myMap.put("count", Operations::count);
        myMap.put("sum", Operations::sum);
        myMap.put("total", Operations::sum);
        myMap.put("min", Operations::min);
        myMap.put("max", Operations::max);
        myMap.put("avg", Operations::average);
        myMap.put("median", Operations::median);
        myMap.put("std", Operations::standardDeviation);
        return myMap;
    }


    public static Long count(Stream<WorkerReport> s) {
        return s.count();
    }

    public static Long sum(Stream<WorkerReport> s) {
        return s.mapToLong(WorkerReport::getLongValue).sum();
    }

    public static Integer max(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).max().orElse(Integer.MIN_VALUE);
    }

    public static Integer min(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).min().orElse(Integer.MAX_VALUE);
    }

    public static Double average(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).average().orElse(0);
    }

    public static Double median(Stream<WorkerReport> s) {
        IntStream sortedValues = s.mapToInt(WorkerReport::getValue).sorted();
        Long size = s.count();
        return size % 2 == 0 ?
                sortedValues.skip(size / 2 - 1).limit(2).average().orElse(0) :
                sortedValues.skip(size / 2).limit(1).average().orElse(0);
    }

    /**
     * Sample Standard Deviation of objects in the provided stream.
     *
     * @param s the Stream of WorkerReport objects.
     * @return the sample standard deviation of the given stream of objects.
     * @see <a href="https://en.wikipedia.org/wiki/Standard_deviation">Standard Deviation</a>
     */
    public static Double standardDeviation(Stream<WorkerReport> s) {
        List<WorkerReport> w = s.collect(Collectors.toList());
        Double avg = average(w.stream());
        Double squaredDiff = w.stream().mapToInt(WorkerReport::getValue)
                .mapToDouble((int i) -> Math.pow(i - avg, 2))
                .sum();
        Double std = Math.sqrt(squaredDiff / (count(w.stream()) - 1));
        return Double.isNaN(std)?0:std;
    }

    /**
     * Receive an array of Strings and use the availableOperations hashmap to convert it to operations. Since the same operation can have multiple names, we have included checks that warn the user if an operation is duplicate and only insert it once
     *
     * @param operations the array
     * @return the {@link Map} with the operation name and operation
     */
    public static Map<String, Function<Stream<WorkerReport>, Number>> parseOperations(String[] operations) {
        Map<String, Function<Stream<WorkerReport>, Number>> res = new HashMap<>();
        for (String op : operations) {
            if (availableOperations.containsKey(op)) res.put(op, availableOperations.get(op));
            else System.err.println(String.format("Operation %s not found", op));
        }
        return res;
    }
}
