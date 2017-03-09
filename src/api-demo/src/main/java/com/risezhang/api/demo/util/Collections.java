package com.risezhang.api.demo.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liang on 09/03/2017.
 */
public class Collections {

    /**
     * Create a collection with a Iterable
     * @param iter
     * @param <E>
     * @return a LinkedList
     */
    public static <E> List<E> makeCollection(Iterable<E> iter) {
        if (iter == null) {
            return null;
        }

        List<E> list = new LinkedList<>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

}
