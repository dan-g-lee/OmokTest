package com.danielglee.omok;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Daniel on 8/20/2017.
 */
public class Board {

    //CONSTANTS
    private final int SIZE = 15;
    private final int WIN_CONDITION = 5;

    //Variables
    /* The Game board */
    private int gameBoard[][];
    /* Sets used to hold pieces in play, used for checking if the win condition has been met */
    /*private Set<String> blackPieces, whitePieces;
    private Set<String> neighbors;*/


    public Board() {
        initialize();
    }

    private void initialize() {
        gameBoard = new int[SIZE][SIZE];
        /*blackPieces = new HashSet<>();
        whitePieces = new HashSet<>();*/
        reset();
    }

    public void reset() {
        for(int ii = 0; ii < SIZE; ii++) {
            for(int jj = 0; jj < SIZE; jj++) {
               gameBoard[ii][jj] = 0;
            }
        }
    }

    public void setPosition(Piece piece, int row, int col) {
        gameBoard[row][col] = piece.getId();
        /*if(piece == Piece.BLACK) blackPieces.add(row + "," + col);
        else whitePieces.add(row + "," + col);*/
    }

    public int getPosition(int row, int col) {
        return gameBoard[row][col];
    }

    public int getRowLength() {
        return SIZE;
    }

    public int getColumnLength() {
        return SIZE;
    }

    public int getWinCondition() {
        return WIN_CONDITION;
    }

    /*public Set<String> getBlackPieces() {
        return blackPieces;
    }

    public Set<String> getWhitePieces() {
        return whitePieces;
    }

    public Set<String> getNeighbors(Piece p, int row, int col) {
        return (p == Piece.BLACK) ? getBlackNeighbors(row, col) : getWhiteNeighbors(row, col);
    }

    private Set<String> getBlackNeighbors(int row, int col) {
        if(row == 0 || row == 1) {
            if(col == 0) {

            }
        }

        if(row - 1 < 1) {

        } else if(row - 2 < 2) {

        } else {

        }
    }

    private Set<String> getWhiteNeighbors(int row, int col) {

    }*/

    public String toString() {
        String builder = "";
        for(int[] row : gameBoard) {
            for(int num : row) {
                builder += "[" + num + "] ";
            }
            builder += "\n";
        }
        return builder;
    }
}
