package com.chess.pieces;

import com.chess.board.BoardModel;

import java.util.ArrayList;

public class MoveList {
    
    private ArrayList<Move> moveList;

    public MoveList() {
        moveList = new ArrayList<>();
    }

    public void addAllDiagonalMoves(BoardModel model, Figure figure) {
        addLineOfMoves(model, figure, 1, 1);
        addLineOfMoves(model, figure, -1, -1);
        addLineOfMoves(model, figure, 1, -1);
        addLineOfMoves(model, figure, -1, 1);
    }

    public void addAllStraightMoves(BoardModel model, Figure figure) {
        addLineOfMoves(model, figure, 0, 1);
        addLineOfMoves(model, figure, 0, -1);
        addLineOfMoves(model, figure, 1, 0);
        addLineOfMoves(model, figure, -1, 0);
    }
    private void addLineOfMoves(BoardModel model, Figure figure,
    int rankDirection, int fileDirection) {

        int nextRank = figure.rank + rankDirection;
        int nextFile = figure.file + fileDirection;
        
        while (model.isWithinBoard(nextRank, nextFile)) {
            Figure nextFigure = model.pieceAt(nextRank, nextFile);
            Player nextPlayer = nextFigure.player;
            if (nextPlayer == figure.player) {
                break;
            }

            addMove(model, figure, nextRank, nextFile);

            if (nextPlayer.isOppositePlayer(figure.player)) {
                break;
            }

            nextRank += rankDirection;
            nextFile += fileDirection;
        }
    }

    public void addMove(BoardModel model, Figure figure, int rank, int file) {
        Move move = new Move(rank, file, figure);
        if (model.isWithinBoard(rank, file)) {
            Player playerAtNewSquare = model.pieceAt(rank, file).player;
            if (playerAtNewSquare != figure.player) {
                moveList.add(move);
            }
        }
    }
    public ArrayList<Move> getMoveList() {
        return moveList;
    }
}