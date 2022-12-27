package com.chess.pieces;

import java.util.Objects;

public class Move {
    private Figure figure;
    private int destinationRank;
    private int destinationFile;

    public Move(int destinationRank, int destinationFile, Figure figure) {
        this.destinationRank = destinationRank;
        this.destinationFile = destinationFile;
        this.figure = figure;
    }

    public int getRank() {
        return destinationRank;
    }

    public int getFile() {
        return destinationFile;
    }

    public Figure getPiece() {
        return figure;
    }

    @Override
    public boolean equals(Object x) {
        if (this == x) return true;
    
        if (x == null || this.getClass() != x.getClass()) return false;
            
        Move c = (Move) x;
        return this.destinationRank == c.destinationRank 
            && this.destinationFile == c.destinationFile
            && this.figure == c.figure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationRank, destinationFile);
    }

    @Override
    public String toString() {
        return figure.toString() +
            " moves to (" + destinationRank + ", " + destinationFile + ")";
    }
}
