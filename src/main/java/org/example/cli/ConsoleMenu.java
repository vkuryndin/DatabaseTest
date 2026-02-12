package org.example.cli;

import org.example.util.InputUtils;

public class ConsoleMenu {


    public void mainLoop() {
        while (true) {
            printMainMenu();
            String choice = InputUtils.readTrimmed("Select ");

            if (choice == null) {
                System.out.println("Input closed. Exiting.");
                return;
            }
            switch (choice) {
                case "1" ->
                    System.out.println("You chose option 1");

                case "2", "q", "quit", "exit" -> {
                    return;
                }
            }
        }
    }
    private void printMainMenu() {
        //TO DO: finish the menu

    }
}

