package com.company;

public class Main {

    public static void main(String[] args) {
        System.out.println(test());
    }

    private static boolean test() {
        boolean result = true;
        SymbolTable symbolTable = new SymbolTable(10);
        symbolTable.add("5");
        symbolTable.add("while");
        symbolTable.add("i");
        symbolTable.add("true");
        result &= symbolTable.contains("5");
        result &= symbolTable.contains("while");
        result &= !symbolTable.contains("jk");
        symbolTable.remove("5");
        result &= !symbolTable.contains("5");
        result &= symbolTable.indexOf("i").getKey() == 5;
        result &= symbolTable.indexOf("i").getValue() == 0;
        return result;
    }

}
