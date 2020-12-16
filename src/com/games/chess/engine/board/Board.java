package com.games.chess.engine.board;

import com.games.chess.engine.Alliance;
import com.games.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

/**
 * Created on 06/12/2020.
 */
public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces, blackPieces;
    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard,
                                                           Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile: gameBoard){
            if(tile.isOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getAlliance() == alliance){
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> currentPieces){
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : currentPieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public Tile getTile(int coordinate) {
        return gameBoard.get(coordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();
        //Black Layout
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        builder.setPiece(new Pawn(12,Alliance.BLACK));
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));
        //White Layout
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setPiece(new Pawn(49,Alliance.WHITE));
        builder.setPiece(new Pawn(50,Alliance.WHITE));
        builder.setPiece(new Pawn(51,Alliance.WHITE));
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public static class Builder {
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i=0; i<BoardUtils.NUM_TILES; i++) {
           String tileText = gameBoard.get(i).toString();
            sb.append(String.format("%3s", tileText));
            if((i+1)% BoardUtils.BOARD_WIDTH == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
