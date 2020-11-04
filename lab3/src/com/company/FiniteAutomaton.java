package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteAutomaton {
    public final List<String> states, alphabet, finalStates;
    public String initialState;
    public final List<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<String, String>, String>> transitions;

    public FiniteAutomaton(String filename) throws IOException {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new ArrayList<>();

        readFiniteAutomaton(filename);
    }

    private void readFiniteAutomaton(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            if (line != null) readStates(line);
            line = br.readLine();
            if (line != null) readAlphabet(line);
            line = br.readLine();
            if (line != null) readInitialState(line);
            line = br.readLine();
            if (line != null) readFinalStates(line);
            while ((line = br.readLine()) != null) {
                readTransition(line);
            }
        }
    }

    private void readTransition(String line) {
        String[] tokens = line.split(" ");
        transitions.add(new AbstractMap.SimpleEntry<>(new AbstractMap.SimpleEntry<>(tokens[0], tokens[1]), tokens[2]));
    }

    private void readFinalStates(String line) {
        finalStates.addAll(Arrays.asList(line.split(" ")));
    }

    private void readInitialState(String line) {
        initialState = line.strip();
    }

    private void readAlphabet(String line) {
        alphabet.addAll(Arrays.asList(line.split(" ")));
    }

    private void readStates(String line) {
        states.addAll(Arrays.asList(line.split(" ")));
    }

    public boolean isDeterministicFiniteAutomaton() {
        for (int i = 0; i < transitions.size() - 1; i++) {
            for (int j = i + 1; j < transitions.size(); j++) {
                if (transitions.get(i).getKey().getKey().equals(transitions.get(j).getKey().getKey()) &&
                        transitions.get(i).getKey().getValue().equals(transitions.get(j).getKey().getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAccepted(String sequence) {
        return false;
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<String, String>, String>> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return "FiniteAutomaton{" +
                "\nstates=" + states.stream().reduce("", (a, b) -> a + b + ",") +
                "\nalphabet=" + alphabet.stream().reduce("", (a, b) -> a + b + ",") +
                "\nfinalStates=" + finalStates.stream().reduce("", (a, b) -> a + b + ",") +
                "\ninitialState='" + initialState + '\'' +
                ", \ntransitions=" + transitions.stream()
                                        .map(line -> line.getKey().getKey() + ", " + line.getKey().getValue() + " -> " + line.getValue())
                                        .reduce("", (a, b) -> a + "\n" + b) +
                '}';
    }
}
