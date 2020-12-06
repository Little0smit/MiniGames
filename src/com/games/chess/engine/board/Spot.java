package com.games.chess.engine.board;

import com.games.chess.engine.pieces.Piece;

/**
 * Created on 06/12/2020.
 */
public abstract class Spot {

    int xCoordinate, yCoordinate;

    Spot(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
    }

    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    public static final class EmptySpot extends Spot {
        EmptySpot(int x, int y){
            super(x,y);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
    public static final class OccupiedSpot extends Spot {
        Piece currentPiece;

        OccupiedSpot(int x, int y, Piece piece){
            super(x, y);
            currentPiece = piece;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return currentPiece;
        }
    }
}
