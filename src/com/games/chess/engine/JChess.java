package com.games.chess.engine;

import com.games.chess.engine.board.Board;

public class JChess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
