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
 * Created on 09/12/2020.
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS = {BoardUtils.BOARD_WIDTH, BoardUtils.BOARD_WIDTH*2, BoardUtils.BOARD_WIDTH-1, BoardUtils.BOARD_WIDTH+1};

    private final int direction;

    public Pawn(int piecePosition, Alliance alliance) {
        super(piecePosition, alliance);
        direction = alliance.getDirection();
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_OFFSETS){
            int candidateDestinationCoordinate = piecePosition + (currentCandidateOffset*direction);
            if (isColumnException(piecePosition, currentCandidateOffset)){
                continue;
            }
            switch (currentCandidateOffset){
                case BoardUtils.BOARD_WIDTH:                                                //MOVE FORWARD 1
                    if(!board.getTile(candidateDestinationCoordinate).isOccupied()){
                        if (candidateDestinationCoordinate < BoardUtils.BOARD_WIDTH){               //Dealing with a Promotion
                            //TODO change to PromotionMove when implemented
                            legalMoves.add(new MainMove(board,this,candidateDestinationCoordinate));
                        } else {
                            legalMoves.add(new MainMove(board,this,candidateDestinationCoordinate));
                        }
                    }
                    break;
                case BoardUtils.BOARD_WIDTH*2:                                              // MOVE FORWARD 2
                    if (!firstMove)                                                             //Only valid on piece's first move
                        break;
                    if(!board.getTile(candidateDestinationCoordinate).isOccupied()
                            && !board.getTile(piecePosition + (BoardUtils.BOARD_WIDTH * direction)).isOccupied()){
                        //TODO change to DoubleMove when implemented
                        legalMoves.add(new MainMove(board,this,candidateDestinationCoordinate));
                    }
                    break;
                case BoardUtils.BOARD_WIDTH-1:                                              //Attack Right
                    //TODO
                    //TODO en passant attack
                    break;
                case BoardUtils.BOARD_WIDTH+1:                                              // Attack Left
                    //TODO
                    // TODO en passant attack
                    break;
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private boolean isColumnException(int piecePosition, int currentCandidateOffset) {
        //TODO complete for each alliance
        int currentColumn = BoardUtils.getColumn(piecePosition);
        switch (currentColumn){
            case 1:
                if(true)
                return true;
                break;
            case BoardUtils.BOARD_WIDTH:
                if(true)
                return true;
                break;
        }
        return false;
    }

    @Override
    public String toString() {
        String piece = pieceType.PAWN.toString();;
        return alliance.isBlack() ? piece : piece.toLowerCase();
    }
}
