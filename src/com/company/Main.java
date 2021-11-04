package com.company;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String again;
        int columnsAmt;
        int rowsAmt;

        do {
            do {
                System.out.println("How many columns do you want the board to have?");
                columnsAmt = intInput();
                System.out.println("How many rows do you want the board to have?");
                rowsAmt = intInput();
                if (columnsAmt > 5 && rowsAmt > 5) {
                    break;
                }
                else {
                    System.out.println("Please enter values bigger than 5.\n");
                }
            } while (true);

            String[][] board = boardInit(columnsAmt, rowsAmt);
            String[][] viewBoard = viewableBoardSetup(columnsAmt, rowsAmt);

            treasureCheck(columnsAmt, rowsAmt, board, viewBoard);

            do {
                System.out.println("Would you like to try again? y/n");
                again = input.next();
                if (!again.equals("y") && !again.equals("n")) {
                    System.out.println("This is not a valid input\nPlease re-try\n");
                } else {
                    break;
                }
            } while (true);
        } while (again.equals("y"));
    }

    static int intInput() {
        Scanner input = new Scanner(System.in);
        int userIn = input.nextInt();
        return userIn;
    }

    static String [][] boardInit(int c,int r) {
        String [][] board = boardSetup(c, r);
        board = coinPlacement(c, r, board);
        return board;
    }
    static String[][] boardSetup(int c, int r) {
        String [][] board = new String[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i][j] = "0";
            }
        }
        return board;
    }
    static String[][] viewableBoardSetup(int c, int r) {
        String [][] viewBoard = new String[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                viewBoard[i][j] = "[]";
                System.out.print(viewBoard[i][j]);
            }
            System.out.println("");
        }
        return viewBoard;
    }
    static String [][] coinPlacement(int c, int r, String [][] b) {
        Random random = new Random();
        ArrayList coordList = new ArrayList();
        int coordMatch = 0;
        int amtCoinDeposits = 0;

        for (int i = 0; i < 2; i++) {
            amtCoinDeposits = random.nextInt(r*c-(r+4));
            if (amtCoinDeposits == 0) {
                i -= 1;
            }
        }
        for (int i = 0; i < amtCoinDeposits; i++) {
            int randXCoord = random.nextInt(c);
            int randYCoord = random.nextInt(r);
            if (coordList.size() == 0) {
                coordList.add(randXCoord);
                coordList.add(randYCoord);
            }
            else {
                for (int j = 0; j < coordList.size() - 1; j++) {
                    if (randXCoord == Integer.parseInt(coordList.get(j).toString()) && randYCoord == Integer.parseInt(coordList.get(j+1).toString())) {
                        i -= 1;
                        j += 1;
                        coordMatch = 1;
                        break;
                    }
                    else {
                        coordMatch = 0;
                    }
                }
                if (coordMatch == 0) {
                    coordList.add(randXCoord);
                    coordList.add(randYCoord);
                }
            }
        }
        for (int i = 0; i < coordList.size(); i++) {
            int randCoins = random.nextInt(10);
            if (randCoins == 0) {
                i -= 1;
            }
            else {
                b[(int) coordList.get(i + 1)][(int) coordList.get(i)] = Integer.toString(randCoins);
                i += 1;
            }
        }
        System.out.println("");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(b[i][j]);
            }
            System.out.println("");
        }
        return b;
    }
    static int[] coordGuess(int c, int r) {
        int[] coords = new int[2];
        int coordXGuess;
        int coordYGuess;
        do {
            System.out.println("Enter the X coordinate (column):");
            coordXGuess = intInput();
            if (coordXGuess > c || coordXGuess <= 0) {
                System.out.println("Not a valid input\nPlease enter another coordinate\n");
            }
        } while (coordXGuess > c || coordXGuess <= 0);
        do {
            System.out.println("Enter the Y coordinate (row)");
            coordYGuess = intInput();
            if (coordYGuess > r || coordYGuess <= 0) {
                System.out.println("Not a valid input\nPlease enter another coordinate\n");
            }
        } while (coordYGuess > r || coordYGuess <= 0);
        coords[0] = coordYGuess;
        coords[1] = coordXGuess;
        return coords;
    }
    static void treasureCheck(int c, int r, String [][] board, String [][] viewBoard) {
        Random random = new Random();
        int movements = ((c * (r + random.nextInt(7)) / 4) / 2) + 1;
        int total = 0;
        int[] coords;
        for (int i = 0; i < movements + 1; i++) {
            coords = coordGuess(c, r);
            int treasureFound = 0;
            for (int j = 0; j < c; j++) {
                for (int k = 0; k < r; k++) {
                    if (Integer.parseInt(board[coords[0] - 1][coords[1] - 1]) > 0 && treasureFound  == 0) {
                        total += Integer.parseInt(board[coords[0] - 1][coords[1] - 1]) * 100;
                        System.out.println("You have found treasure!!\nThere is " + Integer.parseInt(board[coords[0] - 1][coords[1] - 1]) * 100 + " gold coins in the chest.\nYour total is now " + total + " gold coins\nYou have " + (movements - i) + " turns left.\n");
                        viewBoard[coords[0] - 1][coords[1] - 1] = board[coords[0] - 1][coords[1] - 1];
                        board[coords[0] - 1][coords[1] - 1] = "0";
                        treasureFound = 1;
                    }
                }
            }
            if (treasureFound == 0) {
                System.out.println("There was no treasure at those coordinates :(\nYou have " + (movements - i) + " turns left.\n");
            }
            if (movements - i > 0) {
                for (int j = 0; j < r; j++) {
                    for (int k = 0; k < c; k++) {
                        System.out.print(viewBoard[j][k]);
                    }
                    System.out.println("");
                }
            }
        }
        System.out.println("This was your grid:");
        for (int j = 0; j < r; j++) {
            for (int k = 0; k < c; k++) {
                System.out.print(viewBoard[j][k]);
            }
            System.out.println("");
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (Integer.parseInt(board[i][j]) > 0) {
                    viewBoard[i][j] = board[i][j];
                }
            }
        }
        System.out.println("\nThis is the full treasure grid:");
        for (int j = 0; j < r; j++) {
            for (int k = 0; k < c; k++) {
                System.out.print(viewBoard[j][k]);
            }
            System.out.println("");
        }
        System.out.println("\nThe hunt is over!\nYour total gold was " + total + " coins and you had " + movements + " turns.");
    }
}