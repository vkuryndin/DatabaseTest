package org.example.cli;

//enum for console colors
public enum ConsoleColor {

    //color codes
    RED("\u001B[31m"),
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    GREEN( "\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");


    private final String code;

    ConsoleColor(String  code) {
        this.code = code;
    }
    public String wrap (String text) {
        return code + text + RESET.code;
    }
}
