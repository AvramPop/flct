package com.company;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private final List<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>>> pif;

    public ProgramInternalForm() {
        this.pif = new ArrayList<>();
    }

    public void add(AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value) {
        pif.add(value);
    }

    public List<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>>> getPif() {
        return pif;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value : pif)
        {
            result.append(value.getKey());
            result.append(": ");
            result.append(value.getValue().getKey());
            result.append("#");
            result.append(value.getValue().getValue());
            result.append("\n");
        }
        return result.toString();
    }
}
