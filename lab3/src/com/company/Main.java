package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main
{

    private static final List<String> tokens = Arrays
    .asList("begin", "if", "while", "int", "double", "string", "+", "-", "*", "/", "%", "read", "print", ";", "{", "}",
    "[", "]", " ", "==", "<=", ">=", "<", ">", "&&", "!", "||", "\"", "(", ")");

    public static void main(String[] args)
    {
        File file = new File("/home/dani/Desktop/code/scoala/an3/sem1/flct/lab3/src/com/company/p1.txt");
        try
        {
            run(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void run(File file) throws IOException
    {
        SymbolTable symbolTable = new SymbolTable(19);
        ProgramInternalForm programInternalForm = new ProgramInternalForm();
        try (Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset()))
        {
            lines.forEachOrdered(line -> {
                List<String> tokens = generateTokens(line);
                tokens.forEach(token -> {
                    System.out.println("Parsed token: " + token);
                    if (!token.equals(" "))
                    {
                        if (isIdentifier(token) || isConstant(token))
                        {
                            symbolTable.add(token);
                            AbstractMap.SimpleEntry<Integer, Integer> id = symbolTable.indexOf(token);
                            AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value = new AbstractMap.SimpleEntry<>(token, id);
                            programInternalForm.add(value);
                        }
                        else
                        {
                            if (validToken(token))
                            {
                                AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, Integer>> value = new AbstractMap.SimpleEntry<>(token, new AbstractMap.SimpleEntry<>(-1, -1));
                                programInternalForm.add(value);
                            }
                            else
                            {
                                System.err.println("Unknown token " + token);
                            }
                        }
                    }
                });
            });
        }
        System.out.println(symbolTable.toString());
        System.out.println(programInternalForm.toString());
    }

    private static List<String> generateTokens(String line)
    {
        List<String> result = new ArrayList<>();
        String token = "";
        for (int i = 0; i < line.length(); i++)
        {
            if (line.charAt(i) == ' ')
            {
                result.add(token);
                i++;
                token = "";
            }
            System.out.println("token: " + token);
            if (line.charAt(i) == '\"')
            {
                if (!token.equals(""))
                {
                    result.add(token);
                }
                i++;
                token = String.valueOf(line.charAt(i));
                while (line.charAt(i) != '\"')
                {
                    token = token.concat(String.valueOf(line.charAt(i)));
                    i++;
                }
                result.add(token);
                token = "";
            }
            else
                if (isPartOfOperator(line.charAt(i)))
                {
                    if (!token.equals(""))
                    {
                        result.add(token);
                    }
                    token = String.valueOf(line.charAt(i));
                    i++;
                    while (isPartOfOperator(line.charAt(i)))
                    {
                        token = token.concat(String.valueOf(line.charAt(i)));
                        i++;
                    }
                    result.add(token);
                    token = "";
                }
                else
                    if (isSeparator(line.charAt(i)))
                    {
                        if (!token.equals(""))
                        {
                            result.add(token);
                        }
                        result.add(String.valueOf(line.charAt(i)));
                        token = "";
                    }
                    else
                    {
                        token = token.concat(String.valueOf(line.charAt(i)));
                    }
        }
        return result;
    }

    private static boolean isSeparator(char character)
    {
        return character == '[' || character == ']' || character == '{' || character == '}' || character == '(' || character == ',' ||
               character == ')' || character == ';';
    }

    private static boolean isPartOfOperator(char character)
    {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '%' ||
               character == '<' || character == '=' || character == '>' || character == '&' || character == '|' ||
               character == '!';
    }

    private static boolean validToken(String token)
    {
        return tokens.contains(token);
    }

    private static boolean isConstant(String token)
    {
        return token.matches("^(0|[\\+\\-]?[1-9][0-9]*)$|^\\'.\\'$|^\\\".*\\\"$");
    }

    private static boolean isIdentifier(String token)
    {
        return token.matches("^([a-zA-Z])+([a-zA-Z]|[0-9])+$");
    }
}
