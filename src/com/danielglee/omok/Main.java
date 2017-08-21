package com.danielglee.omok;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Daniel on 8/20/2017.
 */
public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Board game = new Board();
        //test();
        play(game);
        System.out.println("Thanks for playing!");
    }

    private static void test() {
        int x[][] = new int[15][15];
        for(int ii = 0; ii < 15; ii++) {
            for(int jj = 0; jj < 15; jj++) {
                x[ii][jj] = 0;
            }
        }
        for(int ii = 0; ii < 15; ii++) {
            for(int jj = 0; jj < 15; jj++) {
                System.out.print("[" + x[ii][jj] + "] ");
            }
            System.out.println();
        }
        System.out.println("\n");

        for(int startingRow = 0; startingRow < 11; startingRow++) {

            for(int ii = startingRow, jj = 14; ii < 15 && jj >= 0; ii++, jj--) {
                x[ii][jj] = 1;
            }
            for(int ii = 0; ii < 15; ii++) {
                for(int jj = 0; jj < 15; jj++) {
                    System.out.print("[" + x[ii][jj] + "] ");
                }
                System.out.println();
            }
            System.out.println();
        }
        for(int ii = 0; ii < 15; ii++) {
            for(int jj = 0; jj < 15; jj++) {
                System.out.print("[" + x[ii][jj] + "] ");
            }
            System.out.println();
        }

    }

    private static void play(Board b) {
        System.out.println("Welcome to Omok!");
        String command;
        boolean turn = true,
                isRunning = true,
                winnerDeclared = false;
        while(isRunning && (!winnerDeclared)) {
            if(turn)
                System.out.println(Piece.BLACK + " turn!");
            else
                System.out.println(Piece.WHITE + " turn!");
            System.out.println(b.toString());
            command = in.nextLine();
            isRunning = parseCommand(command, turn, b);
            winnerDeclared = checkWinCondition(turn, b);
            if(winnerDeclared) System.out.println(((turn) ? Piece.BLACK : Piece.WHITE) + " Wins!");
            in.reset();
            turn = !turn;
        }
        in.close();
    }

    private static boolean parseCommand(String x, boolean t, Board b) {
        if(x.equals("quit")) return false;
        else {
            String[] pos = x.split(" ");
            b.setPosition((t) ? Piece.BLACK : Piece.WHITE, Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
            return true;
        }
    }

    private static boolean checkWinCondition(boolean turn, Board b) {
        Piece piece = (turn) ? Piece.BLACK : Piece.WHITE;
        boolean check = checkRow(piece, b);
        System.out.println("CHECK ROW: " + check);
        check = checkColumn(piece, b);
        System.out.println("CHECK COLUMN: " + check);
        check = checkDiagonalNESW(piece, b);
        System.out.println("CHECK DIAGONAL NESW: " + check);
        check = checkDiagonalNWSE(piece, b);
        System.out.println("CHECK DIAGONAL NWSE: " + check);
        return check;//checkRow(piece, b) | checkColumn(piece, b) | checkDiagonalNESW(piece, b) | checkDiagonalNWSE(piece, b);
    }

    private static boolean checkRow(Piece p, Board b) {
        for(int ii = 0; ii < b.getRowLength(); ii++) {
            int lastCol = -1, currCol, count = 0;
            for(int jj = 0; jj < b.getColumnLength(); jj++) {
                //If the piece exists
                if(b.getPosition(ii, jj) == p.getId()) {
                    //If this is the first piece encountered, set it as last and increment count, otherwise compare
                    if(lastCol == -1) {
                        lastCol = jj;
                        count = 1;
                    } else {
                        currCol = jj;
                        //If current column == current column - 1 (is a neighbor), increment, else reset
                        if(lastCol == currCol - 1) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastCol = currCol;
                    }
                }
            }
            if(count == b.getWinCondition())
                return true;
        }
        return false;
    }

    private static boolean checkColumn(Piece p, Board b) {
        for(int ii = 0; ii < b.getColumnLength(); ii++) {
            int lastRow = -1, currRow, count = 0;
            for(int jj = 0; jj < b.getRowLength(); jj++) {
                if(b.getPosition(jj, ii) == p.getId()) {
                    //If this is the first piece encountered, set it as last and increment count, otherwise compare
                    if(lastRow == -1) {
                        lastRow = jj;
                        count = 1;
                    } else {
                        currRow = jj;
                        //If current row == current row - 1 (is a neighbor), increment, else reset to 1
                        if(lastRow == currRow - 1) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastRow = currRow;
                    }
                }
            }
            if(count == b.getWinCondition())
                return true;
        }
        return false;
    }

    private static boolean checkDiagonalNESW(Piece p, Board b) {
        /* Bottom right trapezoid check */
        for(int startingRow = 0; startingRow < (b.getRowLength() - b.getWinCondition() + 1); startingRow++) {
            int lastRow = -1, lastCol = -1, currRow, currCol, count = 0;
            for(int ii = startingRow, jj = b.getColumnLength() - 1; ii < b.getRowLength() && jj >= 0; ii++, jj--) {
                if(b.getPosition(ii, jj) == p.getId()) {
                    if(lastRow == -1 && lastCol == -1) {
                        lastRow = ii;
                        lastCol = jj;
                        count++;
                    } else {
                        currRow = ii;
                        currCol = jj;
                        if(lastRow == (currRow - 1) && lastCol == (currCol + 1)) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastRow = currRow;
                        lastCol = currCol;
                    }
                }
            }
            if(count == 5) return true;
        }
        /* Top left trapezoid check */
        for(int startingColumn = b.getColumnLength() - 2; startingColumn >= b.getWinCondition() - 1; startingColumn--) {
            int lastRow = -1, lastCol = -1, currRow, currCol, count = 0;
            for(int jj = startingColumn, ii = 0; jj >= 0 && ii < b.getRowLength(); jj--, ii++) {
                if(b.getPosition(ii, jj) == p.getId()) {
                    if(lastRow == -1 && lastCol == -1) {
                        lastRow = ii;
                        lastCol = jj;
                        count++;
                    } else {
                        currRow = ii;
                        currCol = jj;
                        if(lastRow == (currRow - 1) && lastCol == (currCol + 1)) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastRow = currRow;
                        lastCol = currCol;
                    }
                }
            }
            if(count == b.getWinCondition()) return true;
        }
        return false;
    }

    private static boolean checkDiagonalNWSE(Piece p, Board b) {
        /* Bottom left trapezoid check */
        for(int startingRow = 0; startingRow < (b.getRowLength() - b.getWinCondition()); startingRow++) {
            int lastRow = -1, lastCol = -1, currRow, currCol, count = 0;
            for(int ii = startingRow, jj = 0; ii < b.getRowLength() && jj < b.getColumnLength(); ii++, jj++) {
                if(b.getPosition(ii, jj) == p.getId()) {
                    if(lastRow == -1 && lastCol == -1) {
                        lastRow = ii;
                        lastCol = jj;
                        count++;
                    } else {
                        currRow = ii;
                        currCol = jj;
                        if(lastRow == (currRow - 1) && lastCol == (currCol - 1)) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastRow = currRow;
                        lastCol = currCol;
                    }
                }
            }
            if(count == b.getWinCondition()) return true;
        }
        /* Top right trapezoid check */
        for(int startingColumn = 1; startingColumn < (b.getColumnLength() - b.getWinCondition() + 1); startingColumn++) {
            int lastRow = -1, lastCol = -1, currRow, currCol, count = 0;
            for(int jj = startingColumn, ii = 0; jj < b.getColumnLength() && ii < b.getRowLength(); jj++, ii++) {
                if(b.getPosition(ii, jj) == p.getId()) {
                    if(lastRow == -1 && lastCol == -1) {
                        lastRow = ii;
                        lastCol = jj;
                        count++;
                    } else {
                        currRow = ii;
                        currCol = jj;
                        if(lastRow == (currRow - 1) && lastCol == (currCol - 1)) {
                            count++;
                        } else {
                            count = 1;
                        }
                        lastRow = currRow;
                        lastCol = currCol;
                    }
                }
            }
            if(count == b.getWinCondition()) return true;
        }
        return false;
    }
}
