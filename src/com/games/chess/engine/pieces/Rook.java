package com.games.chess.engine.pieces;

import com.games.chess.engine.Alliance;
import com.games.chess.engine.board.Board;
import com.games.chess.engine.board.BoardUtils;
import com.games.chess.engine.board.Move;
import static com.games.chess.engine.board.Move.*;
import com.games.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 06/12/2020.
 */
public class Rook extends Piece{

    private final static int[] CANDIDATE_MOVE_OFFSETS = {-BoardUtils.BOARD_WIDTH, -1, 1, BoardUtils.BOARD_WIDTH};

    Rook(int piecePosition, Alliance alliance) {
        super(piecePosition, alliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_OFFSETS){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                if (isColumnException(candidateDestinationCoordinate, currentCandidateOffset))
                    break;
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isOccupied()){
                        legalMoves.add(new MainMove(board,this,candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance destinationAlliance = pieceAtDestination.getAlliance();
                        if(this.alliance != destinationAlliance){
                            legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private boolean isColumnException(int piecePosition, int currentCandidateOffset) {
        int currentColumn = BoardUtils.getColumn(piecePosition);
        switch (currentColumn){
            case 1: if(currentCandidateOffset == -1)
                return true;
            case BoardUtils.BOARD_WIDTH:if(currentCandidateOffset == 1)
                return true;
        }
        return false;
    }
}
