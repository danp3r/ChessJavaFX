package com.chess.board;


import com.chess.pieces.Bishop;
import com.chess.pieces.Empty;
import com.chess.pieces.Figure;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Move;
import com.chess.pieces.Pawn;
import com.chess.pieces.Player;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

import javax.swing.*;
import java.util.ArrayList;

import static com.chess.pieces.Player.BLACK;
import static com.chess.pieces.Player.UNDEFINED;
import static com.chess.pieces.Player.WHITE;

public class BoardModel {
    
    public static final int SIZE = 8;
    private Figure[][] board;

    public BoardModel() {
        board = new Figure[SIZE][SIZE];
        setUp();
    }

    public void movePieceIfValid(Figure figure, int newRank, int newFile) {
        if (isValidMove(figure, newRank, newFile)) {
            movePiece(figure, newRank, newFile);
        }
    }

    public boolean isValidMove(Figure figure, int newRank, int newFile) {
        boolean validPieceMove = figure.isValidMove(newRank, newFile);
        Player squarePlayer = pieceAt(newRank, newFile).getPlayer();
        boolean notFriendly = squarePlayer != figure.getPlayer();
        boolean castlingMove = isCastlingMove(figure, newRank, newFile);
        
        if (castlingMove && notFriendly) {
            return true;
        }

        Move move = new Move(newRank, newFile, figure);
        boolean inMoveList = figure.getMoveList(this).contains(move);
        return validPieceMove && notFriendly && inMoveList;
    }

    public void castle(Figure figure, int newRank, int newFile) {
        if (newRank != figure.getRank() || !(figure instanceof King)
        || kingIsInCheck(figure.getPlayer())) {
            return;
        }

        King king = (King) figure;
        if (newFile == king.getFile() + 2 && kingCanCastleOO(king)) {
            movePiece(king, newRank, newFile);
            Rook rook = (Rook) pieceAt(newRank, 7);
            movePiece(rook, newRank, newFile - 1);
        }
    }

    public boolean isCastlingMove(Figure figure, int newRank, int newFile) {
        if (!(figure instanceof King)) {
            return false;
        }

        King king = (King) figure;
        return isCastlingOOMove(king, newRank, newFile)
            || isCastlingOOOMove(king, newRank, newFile);
    }

    public boolean isCastlingOOMove(King king, int newRank, int newFile) {
        int castlingRank = (king.getPlayer() == WHITE) ? 7 : 0;
        if (king.getRank() != castlingRank || king.getRank() != newRank) {
            return false;
        }

        return newFile == king.getFile() + 2 && kingCanCastleOO(king);
    }

    public boolean isCastlingOOOMove(King king, int newRank, int newFile) {
        int castlingRank = (king.getPlayer() == WHITE) ? 7 : 0;
        if (king.getRank() != castlingRank || king.getRank() != newRank) {
            return false;
        }

        return newFile == king.getFile() - 2 && kingCanCastleOOO(king);
    }

    public boolean playerCanCastle(Player player) {
        King king = findKing(player);
        return kingCanCastleOO(king) || kingCanCastleOOO(king);
    }

    public boolean kingCanCastle(boolean isShortCastle, King king) {
        Player player = king.getPlayer();
        int rank = king.getRank();
        int file = king.getFile();
        int castlingRank = (player == WHITE) ? 7 : 0;
        int castlingFile = isShortCastle ? 7 : 0;
        Figure rook = pieceAt(castlingRank, castlingFile);
        boolean isRook = rook instanceof Rook && rook.getPlayer() == player;
        boolean empty = castlingSquaresAreEmpty(isShortCastle, rank, file);
        return isRook && rank == castlingRank && file == 4 && empty;
    }

    private boolean castlingSquaresAreEmpty(boolean isShortCastle, int kingRank,
        int kingFile) {
        
        int firstEmptySquareFile = isShortCastle ? kingFile + 1 : kingFile - 1;
        int secondEmptySquareFile = isShortCastle ? kingFile + 2 : kingFile - 2;
        if (!isShortCastle && !pieceAt(kingRank, kingFile - 3).isEmpty()) {
            return false;
        }

        return pieceAt(kingRank, firstEmptySquareFile).isEmpty()
            && pieceAt(kingRank, secondEmptySquareFile).isEmpty();
    }

    public boolean kingCanCastleOO(King king) {
        return kingCanCastle(true, king);
    }

    public boolean kingCanCastleOOO(King king) {
        return kingCanCastle(false, king);
    }

    public void movePiece(Figure figure, int newRank, int newFile) {
        removePiece(figure.getRank(), figure.getFile());
        figure.setRank(newRank);
        figure.setFile(newFile);
        addPiece(figure);

        if (figure instanceof Pawn && (figure.getPlayer() == WHITE && newRank == 0)
            || (figure.getPlayer() == BLACK && newRank == 7)) {
                removePiece(newRank, newFile);
                addPiece(new Queen(newRank, newFile, figure.getPlayer()));
        }
    }

    public Figure pieceAt(int rank, int file) {
        return board[rank][file];
    }

    public boolean isWithinBoard(int rank, int file) {
        boolean rankInBoard = rank >= 0 && rank < SIZE;
        boolean fileInBoard = file >= 0 && file < SIZE;
        return rankInBoard && fileInBoard;
    }

    public void addPiece(Figure figure) {
        board[figure.getRank()][figure.getFile()] = figure;
    }

    public void removePiece(int rank, int file) {
        board[rank][file] = new Empty(rank, file, UNDEFINED);
    }

    public void setUp() {
        setUpPieces(0);
        setUpPawns(1);
        clearRanks(2, 5);
        setUpPawns(6);
        setUpPieces(7);
    }

    private void setUpPieces(int rank) {
        Player player = (rank == 0) ? BLACK : WHITE;
        addPiece(new Rook(rank, 0, player));
        addPiece(new Knight(rank, 1, player));
        addPiece(new Bishop(rank, 2, player));
        addPiece(new Queen(rank, 3, player));
        addPiece(new King(rank, 4, player));
        addPiece(new Bishop(rank, 5, player));
        addPiece(new Knight(rank, 6, player));
        addPiece(new Rook(rank, 7, player));
    }

    private void setUpPawns(int rank) {
        Player player = (rank == 1) ? BLACK : WHITE;
        for (int file = 0; file < SIZE; file++) {
            addPiece(new Pawn(rank, file, player));
        }
    }

    private void clearRanks(int startRank, int endRank) {
        for (int rank = startRank; rank <= endRank; rank++) {
            for (int file = 0; file < SIZE; file++) {
                removePiece(rank, file);
            }
        }
    }
    
    public King findKing(Player player) {
        for (Figure[] row : board) {
            for (Figure figure : row) {
                if (figure instanceof King && player == figure.getPlayer()) {
                    return (King) figure;
                }
            }
        }

        return null;
    }

    public boolean noMovesAvailable(Player player) {
        for (Figure[] row : board) {
            for (Figure figure : row) {
                if (figure.getPlayer() == player) {
                    for (Move move : figure.getMoveList(this)) {
                        if (moveWouldCauseCheck(move, player)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean moveWouldCauseCheck(Move move, Player player) {
        BoardModel copy = deepCopy();
        int rank = move.getRank();
        int file = move.getFile();
        Figure figure = copy.pieceAt(move.getPiece().getRank(), move.getPiece().getFile());
        copy.movePieceIfValid(figure, rank, file);
        boolean isCheck = copy.kingIsInCheck(player);
        if (isCheck) {
            figure.getMoveList(this).remove(move);
        }
        return !isCheck;
    }

    public boolean kingIsInCheck(Player player) {
        King king = findKing(player);
        if (king == null) {
            JOptionPane.showConfirmDialog(null, "GAME END!");
            System.exit(0);
            throw new RuntimeException();
        }
        return squareAttacked(player, king.getRank(), king.getFile());
    }

    public boolean squareAttacked(Player player, int rank, int file) {
        Figure figure = pieceAt(rank, file);
        Bishop fakeBishop = new Bishop(rank, file, player);
        Rook fakeRook = new Rook(rank, file, player);
        Knight fakeKnight = new Knight(rank, file, player);
        return fakePieceMoveListHasPiece(figure, fakeKnight)
            || fakePieceMoveListHasPiece(figure, fakeBishop)
            || fakePieceMoveListHasPiece(figure, fakeRook);
    }

    private boolean fakePieceMoveListHasPiece(Figure figure, Figure fake) {
        for (Move move : fake.getMoveList(this)) {
            Figure attacker = pieceAt(move.getRank(), move.getFile());
            ArrayList<Move> attackList = attacker.getMoveList(this);
            for (Move m : attackList) {
                if (m.getRank() == figure.getRank()
                && m.getFile() == figure.getFile()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isStaleMate() {
        return isStaleMateHelper(WHITE) && isStaleMateHelper(BLACK);
    }

    private boolean isStaleMateHelper(Player player) {
        return noMovesAvailable(player) && !kingIsInCheck(player);
    }

    public boolean isCheckMate(Player player) {
        return noMovesAvailable(player) && kingIsInCheck(player);
    }

    public BoardModel deepCopy() {
        BoardModel newModel = new BoardModel();
        newModel.clear();
        for (Figure[] row : board) {
            for (Figure figure : row) {
                newModel.addPiece(figure.deepCopy());
            }
        }

        return newModel;
    }

    public void clear() {
        clearRanks(0, 7);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Figure[] row : board) {
            for (Figure figure : row) {
                char letter = pieceToLetter(figure);
                result.append(String.valueOf(letter) + ' ');
            }

            result.append('\n');
        }

        return result.toString();
    }

    // Returns the letter a piece is represented by in algebraic chess notation.
    private char pieceToLetter(Figure figure) {
        // Get the first letter of the piece's class name
        char letter = figure.getClass().getSimpleName().charAt(0);

        // A Knight's letter in algebraic chess notation is 'N', not 'K',
        // because King already uses 'K'.
        if (figure instanceof Knight) {
            letter = 'N';
        }

        // Lowercase letters are for white pieces, and uppercase for black.
        if (figure.getPlayer() == WHITE) {
            letter = Character.toLowerCase(letter);
        }

        if (figure.isEmpty()) {
            letter = '.';
        }

        return letter;
    }
}