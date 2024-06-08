package com.example.newsandroidproject.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueList<E> extends ArrayList<E> {
    private Set<E> set = new LinkedHashSet<>();

    @Override
    public boolean add(E e) {
        if (set.add(e)) {
            return super.add(e);
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (set.add(e)) {
                super.add(e);
                modified = true;
            }
        }
        return modified;
    }
}
