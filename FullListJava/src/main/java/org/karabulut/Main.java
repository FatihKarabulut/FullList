package org.karabulut;

import org.karabulut.collection.FullList;

public class Main {
    public static void main(String[] args) {
        var list = new FullList<Integer>();
        var list1 = new FullList<Integer>();

        list.add(1);
        list.add(12);
        list.add(123);


        System.out.println(list);

    }
}