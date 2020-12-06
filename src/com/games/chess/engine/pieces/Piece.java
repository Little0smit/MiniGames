package com.games.chess.engine.pieces;

import com.games.chess.engine.Alliance;
import com.games.chess.engine.board.Board;
import com.games.chess.engine.board.Move;

import java.util.Collection;

/**
 * Created on 06/12/2020.
 */
public abstract class Piece{
    protected final int piecePosition;
    protected final Alliance colour;

    Piece(final int piecePosition, final Alliance colour){
        this.piecePosition = piecePosition;
        this.colour = colour;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);


    public Alliance getAlliance() {
        return colour;
    }
}
