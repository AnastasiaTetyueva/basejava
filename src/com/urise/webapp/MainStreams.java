package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        //System.out.println(minValue(new int[]{1,2,3,3,2,3}));

        List<Integer> list = Arrays.asList(3, 12, 13, 7, 5, 18, 8);
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct()
                .sorted()
                .reduce(0, (subresult, value) ->
                    subresult * 10 + value);
    }

    // 1
    /*
    private static List<Integer> oddOrEven(List<Integer> integers) {
       return ((integers.stream().reduce(0, Integer::sum) %2 == 0)
                    ?  integers.stream().filter(x -> x % 2 != 0)
                    :  integers.stream().filter(x -> x % 2 == 0))
                .collect(Collectors.toList());
    } */

    // 2
    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }

}
