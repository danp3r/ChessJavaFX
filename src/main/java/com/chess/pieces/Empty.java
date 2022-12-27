package com.chess.pieces;

import com.chess.board.BoardModel;

import java.util.ArrayList;

public class Empty extends Figure {

    public Empty(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        return false;
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Figure deepCopy() {
        return new Empty(rank, file, player);
    }
}