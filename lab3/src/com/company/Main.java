package com.company;

public class Main {

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(10);
        symbolTable.add("5");
        symbolTable.add("5");
        System.out.println(symbolTable.toString());
        System.out.println(symbolTable.indexOf("5"));
    }
}
