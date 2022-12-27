package com.chess.board;

import com.chess.pieces.Figure;
import com.chess.pieces.King;
import com.chess.pieces.Player;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BoardController implements EventHandler<MouseEvent> {
    
    private final BoardModel model;
    private final Game game;
    private final BoardView view;

    private Figure prev = null;

    public BoardController() {
        model = new BoardModel();
        game = new Game(model); 
        view = new BoardView();
    }

    public BoardView getView() {
        return view;
    }

    public void movePiece(Figure figure, int newRank, int newFile) {
        if (model.isValidMove(figure, newRank, newFile)
            && game.getCurrentPlayer() == figure.getPlayer()) {
        
            if (model.isCastlingMove(figure, newRank, newFile)) {
                King king = (King) figure;
                int rookFile = model.isCastlingOOMove(king, newRank, newFile) ? 7 : 0;
                Figure rook = model.pieceAt(king.getRank(), rookFile);
                castle(king, rook, newRank, newFile);
            } else {
                view.movePiece(figure, newRank, newFile);
                game.playMove(figure, newRank, newFile);
                model.movePiece(figure, newRank, newFile);
            }
        }
    }

    public void castle(Figure king, Figure rook, int newRank, int newFile) {
        view.movePiece(king, newRank, newFile);
        model.movePiece(king, newRank, newFile);
        game.playMove(king, newRank, newFile);

        int rookCastlingFile = rook.getFile() == 7 ? newFile - 1 : newFile + 1;
        view.movePiece(rook, newRank, rookCastlingFile);
        model.movePiece(rook, newRank, rookCastlingFile);
    }

    @Override
    public void handle(MouseEvent event) {
        int rank = (int) event.getY()/BoardView.SQUARE_SIZE;
        int file = (int) event.getX()/BoardView.SQUARE_SIZE;

        Figure figure = model.pieceAt(rank, file);
        if (prev != null) {
            view.unhighlightAll();
            movePiece(prev, rank, file);
            view.highlightKingIfAttacked(model, Player.WHITE);
            view.highlightKingIfAttacked(model, Player.BLACK); 
        } 
        
        if (prev == null || (game.getCurrentPlayer() == figure.getPlayer()
            && model.pieceAt(rank, file) == figure)) {
            view.highlightMoves(model, figure);
            view.highlightCastlingMoves(model, figure, file);
        }

        prev = figure;
    }
}
