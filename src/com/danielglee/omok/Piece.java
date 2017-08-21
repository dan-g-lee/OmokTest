package com.danielglee.omok;

/**
 * Created by Daniel on 8/20/2017.
 */
public enum Piece {
    BLACK(1), WHITE(2);
    private final int id;
    Piece(int id) { this.id = id; }
    public int getId() { return this.id; }
}
