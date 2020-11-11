package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteAutomaton {
    public List<String> states, alphabet, finalStates;
    public String initialState;
    public Map<AbstractMap.SimpleEntry<String, String>, List<String>> transitions;

    public FiniteAutomaton(String filename) throws IOException {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new HashMap<>();

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
        AbstractMap.SimpleEntry<String, String> key = new AbstractMap.SimpleEntry<>(tokens[0], tokens[1]);
        if (transitions.containsKey(key)) {
            transitions.get(key).add(tokens[2]);
        } else {
            transitions.put(key, new ArrayList<>(Collections.singletonList(tokens[2])));
        }
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
        return transitions.values().stream().map(List::size).noneMatch(size -> size > 1);
    }

    public boolean validateSelf() {
        if (!states.contains(initialState)) return false;
        if (finalStates.stream().anyMatch(finalState -> !states.contains(finalState))) return false;
        return transitions.entrySet().stream().noneMatch(entry -> {
            boolean invalidInitialState = !states.contains(entry.getKey().getKey());
            boolean invalidTerminal = !alphabet.contains(entry.getKey().getValue());
            boolean invalidFinalState = entry.getValue().stream().anyMatch(finalState -> !states.contains(finalState));
            return invalidInitialState || invalidTerminal || invalidFinalState;
        });
    }

    public boolean accepts(String sequence) {
        if (isDeterministicFiniteAutomaton()) {
            String currentState = initialState;
            for (char c : sequence.toCharArray()) {
                if (transitions.containsKey(new AbstractMap.SimpleEntry<>(currentState, String.valueOf(c)))) {
                    currentState = transitions.get(new AbstractMap.SimpleEntry<>(currentState, String.valueOf(c))).get(0);
                } else {
                    return false;
                }
            }
            return finalStates.contains(currentState);
        } else {
            System.err.println("This is not a DFA.");
            return false;
        }
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

    public Map<AbstractMap.SimpleEntry<String, String>, List<String>> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return "FiniteAutomaton{" +
                "\nstates=" + states.stream().reduce("", (a, b) -> a + b + ",") +
                "\nalphabet=" + alphabet.stream().reduce("", (a, b) -> a + b + ",") +
                "\nfinalStates=" + finalStates.stream().reduce("", (a, b) -> a + b + ",") +
                "\ninitialState='" + initialState + '\'' +
                ", \ntransitions=" +
                transitions.entrySet().stream()
                        .map(entry -> entry.getKey().getKey() + ", " + entry.getKey().getValue() + " -> " + entry.getValue().stream().reduce("", (a, b) -> a + b + ","))
                        .reduce("", (a, b) -> a + "\n" + b) +
                '}';
    }
}
