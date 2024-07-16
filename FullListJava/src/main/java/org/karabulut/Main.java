package org.karabulut;

import org.karabulut.collection.FullList;

public class Main {
    public static void main(String[] args) {
        var list = new FullList<Integer>();
        var list1 = new FullList<Integer>();

        list.add(50);
        list.add(100);
        list.add(100);
        list.add(100);
        list.add(10);

        list.forEach(a -> System.out.println(a));
        System.out.println("----------------");
        list.distinct();
        list.forEach(a -> System.out.println(a));
        System.out.println("----------------");

    }
}