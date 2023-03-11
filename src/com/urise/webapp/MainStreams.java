package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1,2,3,3,2,3}));

        List<Integer> list = Arrays.asList(11, 2, 3, 4, 5, 8);
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct()
                .sorted()
                .reduce(0, (subresult, value) ->
                    subresult * 10 + value);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
       return ((integers.stream().reduce(0, Integer::sum) %2 == 0)
                    ?  integers.stream().filter(x -> x % 2 != 0)
                    :  integers.stream().filter(x -> x % 2 == 0))
                .collect(Collectors.toList());
    }

}
