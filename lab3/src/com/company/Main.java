package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    private static Validators validators;
    private static int lineCount = 1;

    public static void main(String[] args) throws IOException {
        validators = new Validators();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                            if (validators.isIdentifier(token) || validators.isConstant(token)) {
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
        if (validators.isConstant(token)) {
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
