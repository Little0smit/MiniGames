package com.games.chess.engine.board;

/**
 * Created on 06/12/2020.
 */
public class BoardUtils {
    public static final int BOARD_WIDTH = 8;
    public static final int NUM_TILES = 64;

    private BoardUtils(){
        throw new RuntimeException("Not to be instantiated");
    }
    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getColumn(final int piecePosition) {
        if(piecePosition >= NUM_TILES || piecePosition < 0)
            return -1;
        return piecePosition%BOARD_WIDTH+1;
    }
}
