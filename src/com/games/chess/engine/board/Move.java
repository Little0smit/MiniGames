package com.games.chess.engine.board;

import com.games.chess.engine.pieces.Piece;

/**
 * Created on 06/12/2020.
 */
public abstract class Move {

    private final Board board;
    private final Piece piece;
    private final int destinationCoordinate;

    private Move(final Board board, final Piece piece, final int destinationCoordinate) {
        this.board = board;
        this.piece = piece;
        this.destinationCoordinate = destinationCoordinate;
    }


    public static final class MainMove extends Move{

        public MainMove(final Board board,
                        final Piece piece,
                        final int destinationCoordinate) {
            super(board, piece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move{
        private final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece piece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }

    //TODO implement DoubleMove
    //TODO implement PromotionMove

}
