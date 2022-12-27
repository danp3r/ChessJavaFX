package com.chess.pieces;

import java.util.ArrayList;
import com.chess.board.BoardModel;

public class King extends Figure {

    public King(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        if (newRank == rank && newFile == file) {
            return false;
        }

        int diffRank = Math.abs(newRank - rank);
        int diffFile = Math.abs(newFile - file);
        return diffRank <= 1 && diffFile <= 1;
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addMove(model, this, rank - 1, file - 1);
        moveList.addMove(model, this, rank, file - 1);
        moveList.addMove(model, this, rank, file + 1);
        moveList.addMove(model, this, rank + 1, file - 1);
        moveList.addMove(model, this, rank + 1, file);
        moveList.addMove(model, this, rank + 1, file + 1);     
        moveList.addMove(model, this, rank - 1, file);
        moveList.addMove(model, this, rank - 1, file + 1);

        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Figure deepCopy() {
        return new King(rank, file, player);
    }
    
}