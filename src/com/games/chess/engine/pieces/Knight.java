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
public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS = {-17,-15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Alliance colour) {
        super(piecePosition, colour);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_OFFSETS){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                if(isColumnException(this.piecePosition,currentCandidateOffset)){
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isOccupied()){
                    legalMoves.add(new MainMove(board,this,candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance destinationAlliance = pieceAtDestination.getAlliance();
                    if(this.colour != destinationAlliance){
                        legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private boolean isColumnException(int piecePosition, int currentCandidateOffset) {
        int currentColumn = BoardUtils.getColumn(piecePosition);
        switch (currentColumn){
            case 1: if(currentCandidateOffset == -17 || currentCandidateOffset == -10 || currentCandidateOffset == 6 || currentCandidateOffset == 15)
                return true;
            case 2:if(currentCandidateOffset == -10 || currentCandidateOffset == 6)
                return true;
            case 7:if(currentCandidateOffset == -6 || currentCandidateOffset == 10)
                return true;
            case 8:if(currentCandidateOffset == -15 || currentCandidateOffset == -6 || currentCandidateOffset == 10 || currentCandidateOffset == 17)
                return true;
        }
        return false;
    }
}
