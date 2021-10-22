package com.company;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("How many columns do you want the board to have?");
        int columnsAmt = intInput();
        System.out.println("How many rows do you want the board to have?");
        int rowsAmt = intInput();

        boardInit(columnsAmt,rowsAmt);
    }
    
    static int intInput() {
        Scanner input = new Scanner(System.in);
        int userIn = input.nextInt();
        return userIn;
    }
    
    static void boardInit(int c,int r) {
        String [][] board = boardSetup(c, r);
        coinPlacement(c, r, board);
    }
    static String[][] boardSetup(int c, int r) {
        String [][] board = new String[c][r];

        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                board[i][j] = "[]";
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }
        return board;
    }
    static void coinPlacement(int c, int r, String [][] b) {
        Random random = new Random();
        ArrayList coordList = new ArrayList();
        
        for (int i = 0; i < random.nextInt(r*c-(r+3)); i++) {
            int randCoins = random.nextInt(1001);
            int randXCoord = random.nextInt(c);
            int randYCoord = random.nextInt(r);
            String concatCoords = Integer.toString(randXCoord) + "," + Integer.toString(randYCoord);
            for (int j = 0; j < coordList.size(); j++) {
                if (concatCoords.equals(coordList.get(j))) {
                    i -= 1;
                }
                else {
                    coordList.add(concatCoords);
                }
            }
        }
    }
}
