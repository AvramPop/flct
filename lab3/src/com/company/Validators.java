package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Validators {
    private final List<String> tokens;

    public Validators() throws IOException {
        tokens =
                Files.readAllLines(Path.of("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/tokens.in")).stream()
                        .map(line -> Arrays.asList(line.split(" "))).flatMap(List::stream).collect(Collectors.toList());
    }

    public boolean isOperator(String token) {
        return token.equals("+") ||
                token.equals("-") ||
                token.equals("*") ||
                token.equals("/") ||
                token.equals("%") ||
                token.equals("<") ||
                token.equals("<=") ||
                token.equals("==") ||
                token.equals("!=") ||
                token.equals(">") ||
                token.equals(">=") ||
                token.equals("&&") ||
                token.equals("||") ||
                token.equals("!");
    }

    public boolean isSeparator(char character) {
        return character == '[' || character == ']' || character == '{' || character == '}' || character == '(' ||
                character == ',' || character == ')' || character == ';' || character == ' ';
    }

    public boolean isPartOfOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '%' ||
                character == '<' || character == '=' || character == '>' || character == '&' || character == '|' ||
                character == '!';
    }

    public boolean isValidToken(String token) {
        return tokens.contains(token);
    }

    public boolean isDouble(String token) {
        return token.matches("^^[\\+\\-]?(0|([1-9][0-9]*))(\\.[0-9]*[1-9]+)$");
    }

    public boolean isIdentifier(String token) {
        return token.matches("^([a-zA-Z])+([a-zA-Z]|[0-9])*$");
    }
}
