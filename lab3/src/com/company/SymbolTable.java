package com.company;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    public final int SIZE;
    private final List<List<String>> hashTable;

    public SymbolTable(int size) {
        this.SIZE = size;
        hashTable = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            hashTable.add(new ArrayList<>());
        }
    }

    private int hash(String value) {
        return value.hashCode() % SIZE;
    }

    public void add(String value) {
        hashTable.get(hash(value)).add(value);
    }

    public boolean contains(String value) {
        return hashTable.get(hash(value)).contains(value);
    }

    public void remove(String value) {
        if (contains(value)) {
            hashTable.get(hash(value)).remove(value);
        }
    }

    public Map.Entry<Integer, Integer> indexOf(String value) {
        return new AbstractMap.SimpleEntry<>(hash(value), hashTable.get(hash(value)).indexOf(value));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Symbol table: \n");
        for (int i = 0; i < SIZE; i++) {
            result.append(i);
            result.append(": ");
            for (String value : hashTable.get(i)) {
                result.append(value).append(", ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
