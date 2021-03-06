package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static Validators validators;
    private static int lineCount = 1;
    private static FiniteAutomaton identifierFiniteAutomaton, stringFiniteAutomaton, intFiniteAutomaton, doubleFiniteAutomaton;

    public static void main(String[] args) throws IOException {
        validators = new Validators();
        try {
            fa();
//            System.out.println(finiteAutomaton.validateSelf());
//            System.out.println(identifierFiniteAutomaton.accepts(")Aa88"));
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fa() throws IOException {
        identifierFiniteAutomaton = new FiniteAutomaton("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/identifierDFA.in");
        stringFiniteAutomaton = new FiniteAutomaton("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/stringDFA.in");
        stringFiniteAutomaton.alphabet.add(" ");
        stringFiniteAutomaton.transitions.put(new AbstractMap.SimpleEntry<>("B", " "), new ArrayList<>(Collections.singletonList("B")));
        intFiniteAutomaton = new FiniteAutomaton("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/intDFA.in");
        doubleFiniteAutomaton = new FiniteAutomaton("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/doubleDFA.in");
        //        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Hello:");
//        System.out.println("1 - states");
//        System.out.println("2 - alphabet");
//        System.out.println("3 - transitions");
//        System.out.println("4 - final states");
//        System.out.println("0 - exit");
//        int input = keyboard.nextInt();
//        while (input != 0) {
//            switch (input) {
//                case 1:
//                    System.out.println(finiteAutomaton.states.stream().reduce("", (a, b) -> a + b + ","));
//                    break;
//
//                case 2:
//                    System.out.println(finiteAutomaton.alphabet.stream().reduce("", (a, b) -> a + b + ","));
//                    break;
//
//                case 3:
//                    System.out.println(finiteAutomaton.transitions.entrySet().stream()
//                            .map(entry -> entry.getKey().getKey() + ", " + entry.getKey().getValue() + " -> " + entry.getValue().stream().reduce("", (a, b) -> a + b + ","))
//                            .reduce("", (a, b) -> a + "\n" + b));
//                    break;
//
//                case 4:
//                    System.out.println(finiteAutomaton.finalStates.stream().reduce("", (a, b) -> a + b + ","));
//                    break;
//
//                case 0:
//                    System.out.println("bye");
//
//                default:
//                    System.out.println("Wrong input");
//            }
//            input = keyboard.nextInt();
//        }
//         System.out.println(finiteAutomaton.toString());
//         System.out.println(finiteAutomaton.isDeterministicFiniteAutomaton());
    }

    private static void run() throws IOException {
        SymbolTable symbolTable = new SymbolTable(19);
        ProgramInternalForm programInternalForm = new ProgramInternalForm();
        StringBuilder errorMessage = new StringBuilder();
        try (Stream<String> lines = Files
                .lines(Path.of("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/p1.txt"),
                        Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                List<String> tokens = generateTokens(line);
                tokens.forEach(token -> {
                    if (!token.equals(" ")) {
                        if (validators.isValidToken(token)) {
                            addOtherToken(programInternalForm, token);
                        } else {
                            if (identifierFiniteAutomaton.accepts(token) || stringFiniteAutomaton.accepts(token) || intFiniteAutomaton.accepts(token) || validators.isDouble(token)) {
                                addConstantOrIdentifier(symbolTable, programInternalForm, token, validators);
                            } else {
                                errorMessage.append("Lexical error: Unknown token '").append(token).append("' @ line ").append(lineCount).append("\n");
                            }
                        }
                    }
                });
                lineCount++;
            });
        }
        Files.write(Paths.get("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/pif.out"), programInternalForm.toString().getBytes());
        Files.write(Paths.get("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/st.out"), symbolTable.toString().getBytes());
        if (errorMessage.length() == 0) {
            System.out.println("Program successfully ran");
        } else {
            System.err.println(errorMessage.toString());
        }
    }

    private static void addOtherToken(ProgramInternalForm programInternalForm, String token) {
        AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value =
                new AbstractMap.SimpleEntry<>(token, new AbstractMap.SimpleEntry<>(-1, -1));
        programInternalForm.add(value);
    }

    private static void addConstantOrIdentifier(SymbolTable symbolTable, ProgramInternalForm programInternalForm, String token, Validators validators) {
        symbolTable.add(token);
        String pifInput;
        if (stringFiniteAutomaton.accepts(token) || intFiniteAutomaton.accepts(token) || validators.isDouble(token)) {
            pifInput = "const";
        } else {
            pifInput = "id";
        }
        AbstractMap.SimpleEntry<Integer, Integer> id = symbolTable.indexOf(token);
        AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value =
                new AbstractMap.SimpleEntry<>(pifInput, id);
        programInternalForm.add(value);
    }

    private static List<String> generateTokens(String line) {
        List<String> result = new ArrayList<>();
        String token = "";
        int i = 0;
        while (i < line.length()) {
            if (line.charAt(i) == '\"') {
                if (!token.equals("")) {
                    result.add(token);
                }
                token = String.valueOf(line.charAt(i));
                i++;
                if (i >= line.length()) break;
                while (line.charAt(i) != '\"') {
                    token = token.concat(String.valueOf(line.charAt(i)));
                    i++;
                    if (i >= line.length()) break;
                }
                if (i >= line.length()) break;
                token = token.concat(String.valueOf(line.charAt(i)));
                result.add(token);
                i++;
                token = "";
            } else if (validators.isPartOfOperator(line.charAt(i))) {
                if (!token.equals("")) {
                    result.add(token);
                }
                token = String.valueOf(line.charAt(i)) + line.charAt(i + 1);
                if (validators.isOperator(token)) {
                    result.add(token);
                    i = i + 2;
                    token = "";
                } else {
                    token = token.substring(0, token.length() - 1);
                    if ((token.equals("+") || token.equals("-")) && String.valueOf(line.charAt(i + 1)).matches("[0-9]")) {
                        i = i + 1;
                    } else {
                        result.add(token);
                        i = i + 1;
                        token = "";
                    }
                }
            } else if (validators.isSeparator(line.charAt(i))) {
                if (!token.equals("")) {
                    result.add(token);
                }
                result.add(String.valueOf(line.charAt(i)));
                i++;
                token = "";
            } else {
                if (line.charAt(i) != ' ') {
                    token = token.concat(String.valueOf(line.charAt(i)));
                }
                i++;
            }
        }
        if (!token.equals("")) {
            result.add(token);
        }
        return result;
    }
}
