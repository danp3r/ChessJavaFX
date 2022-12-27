package com.chess.pieces;

import com.chess.board.BoardModel;

import java.util.ArrayList;

public class Knight extends Figure {
    
    public Knight(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        int diffRank = Math.abs(newRank - rank);
        int diffFile = Math.abs(newFile - file);

        return (diffRank == 2 && diffFile == 1) 
            || (diffRank == 1 && diffFile == 2);
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addMove(model, this, rank - 2, file - 1);
        moveList.addMove(model, this, rank - 2, file + 1);
        moveList.addMove(model, this, rank - 1, file - 2);
        moveList.addMove(model, this, rank - 1, file + 2);
        moveList.addMove(model, this, rank + 1, file - 2);
        moveList.addMove(model, this, rank + 1, file + 2);     
        moveList.addMove(model, this, rank + 2, file - 1);
        moveList.addMove(model, this, rank + 2, file + 1);      
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Figure deepCopy() {
        return new Knight(rank, file, player);
    }
}