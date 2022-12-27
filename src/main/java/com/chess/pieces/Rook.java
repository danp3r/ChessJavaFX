package com.chess.pieces;

import com.chess.board.BoardModel;

import java.util.ArrayList;

public class Rook extends Figure {
    private boolean rookHasMoved;

    public Rook(int rank, int file, Player player) {
        super(rank, file, player);
        rookHasMoved = false;
    }

    public void setRookHasMoved() {
        rookHasMoved = true;
    }

    public boolean rookHasMoved() {
        return rookHasMoved;
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        return (newRank == rank) ^ (newFile == file);
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addAllStraightMoves(model, this);
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Figure deepCopy() {
        return new Rook(rank, file, player);
    }
}