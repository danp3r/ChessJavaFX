package com.chess.board;

import com.chess.pieces.Figure;
import com.chess.pieces.IllegalPlayerException;
import com.chess.pieces.Move;
import com.chess.pieces.Player;

import java.util.ArrayList;

import static com.chess.board.GameState.ONGOING;
import static com.chess.pieces.Player.BLACK;
import static com.chess.pieces.Player.WHITE;

public class Game {
    private GameState state;
    private Player currentPlayer;
    private BoardModel model;
    private ArrayList<Move> moves;

    public Game() {
        state = ONGOING;
        currentPlayer = WHITE;
        model = new BoardModel();
        moves = new ArrayList<>();
    }

    public Game(BoardModel model) {
        state = ONGOING;
        currentPlayer = WHITE;
        this.model = model;
        moves = new ArrayList<>();
    }

    public BoardModel getModel() {
        return model;
    }

    public ArrayList<Move> getGameMoveList() {
        return moves;
    }

    public GameState getGameState() {
        return state;
    }

    public void addMove(int rank, int file, Figure figure) {
        if (figure.getPlayer() != currentPlayer) {
            throw new IllegalPlayerException();
        }

        moves.add(new Move(rank, file, figure));
        model.movePieceIfValid(figure, rank, file);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void playMove(Figure figure, int rank, int file) {
        addMove(rank, file, figure);
        currentPlayer = nextPlayer();
    }

    public Player nextPlayer() {
        return (currentPlayer == WHITE) ? BLACK : WHITE;
    }

    public void reset() {
        state = ONGOING;
        currentPlayer = WHITE;
        model.setUp();
        moves.clear();
    }
}