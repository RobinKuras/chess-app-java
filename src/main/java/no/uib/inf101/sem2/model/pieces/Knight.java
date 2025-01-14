package no.uib.inf101.sem2.model.pieces;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.model.ChessBoard;
import no.uib.inf101.sem2.model.ChessModel;
import no.uib.inf101.sem2.model.Move;
import no.uib.inf101.sem2.model.Tile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Knight implements IChessPiece{
    private final ChessModel model;
    private final ChessBoard board;
    private CellPosition pos;
    private final ChessAlliance pieceColor;
    private final ImageIcon imageIcon;
    private final List<Move> candidateMoves = new ArrayList<>();
    private boolean isAttacking;
    /**
     * The constructor of the chess piece representing knights.
     * @param model the model the piece is a part of, to gain access to all other pieces positions.
     * @param position the current position on the chess board.
     * @param color the chess alliance of the piece. WHITE/BLACK
     */
    public Knight(ChessModel model, CellPosition position, ChessAlliance color){
        this.pos = position;
        this.pieceColor = color;
        this.model = model;
        this.board = model.getBoard();
        this.isAttacking = false;

        if(this.pieceColor == ChessAlliance.WHITE){
            this.imageIcon = new ImageIcon("src/main/resources/Chess_White-Knight.png");
        } else {
            this.imageIcon = new ImageIcon("src/main/resources/Chess_Black-Knight.png");
        }
    }

    @Override
    public void addCandidateMove(Move move){
        if(!resultsInCheck(move)){
            candidateMoves.add(move);
        } else if(board.get(move.getDestination()).getPiece() != null && board.get(move.getDestination()).getPiece().isAttacking()){
            candidateMoves.add(move);
        }
    }

    @Override
    public boolean resultsInCheck(Move move){
        ChessAlliance alliance = model.getCurrentPlayersTurn();
        CellPosition kingPos = model.getKingPosition(alliance);
        ChessAlliance oppAlliance;

        if (alliance == ChessAlliance.WHITE) {
            oppAlliance = ChessAlliance.BLACK;
        } else {
            oppAlliance = ChessAlliance.WHITE;
        }

        for(GridCell<Tile> cell : model.getTilesOnBoard()){
            Tile tile = cell.value();
            if (tile.getPiece() != null) {
                if (tile.getPiece().getAlliance() == oppAlliance) {
                    for(Move candMove : tile.getPiece().getCandidateMoves()){
                        if(candMove.getDestination().equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        } return false;
    }

    @Override
    public void movePiece(Move move) {
        if (candidateMoves.contains(move)) {
            this.pos = new CellPosition(this.pos.row() + move.deltaPos().row(), this.pos.col() + move.deltaPos().col());
        }
    }



    @Override
    public void updateCandidateMoves() {
        this.candidateMoves.clear();

        int row = pos.row();
        int col = pos.col();
        CellPosition target;

        //checks if L-moves to the top right is legal
        target = new CellPosition(row-2,col+1);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(-2,1)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(-2,1)));
        }

        target = new CellPosition(row-1,col+2);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(-1,2)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(-1,2)));
        }

        //checks if L-movement to the top left is legal
        target = new CellPosition(row-2,col-1);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(-2,-1)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(-2,-1)));
        }

        target = new CellPosition(row-1,col-2);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(-1,-2)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(-1,-2)));
        }
        //checks if L-moves to the bottom right is legal
        target = new CellPosition(row+2,col+1);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(2,1)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(2,1)));
        }

        target = new CellPosition(row+1,col+2);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(1,2)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(1,2)));
        }

        //checks if L-movement to the bottom left is legal
        target = new CellPosition(row+2,col-1);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(2,-1)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(2,-1)));
        }

        target = new CellPosition(row+1,col-2);
        if(board.positionIsOnGrid(target)){
            if(board.isOccupied(target)){
                if(board.getPieceAt(target).getAlliance() != pieceColor){
                    addCandidateMove(new Move(this,new CellPosition(1,-2)));
                }
            } else addCandidateMove(new Move(this,new CellPosition(1,-2)));
        }
    }

    @Override
    public boolean isAttacking() {
        ChessAlliance oppAlliance;

        if (pieceColor == ChessAlliance.WHITE) {
            oppAlliance = ChessAlliance.BLACK;
        } else {
            oppAlliance = ChessAlliance.WHITE;
        }

        for(Move move : getCandidateMoves()){
            if(move.getDestination().equals(model.getKingPosition(oppAlliance))){
                this.isAttacking = true;
            } else this.isAttacking = false;
        }
        return this.isAttacking;
    }

    @Override
    public String getImageFilePath() {
        return imageIcon.toString();
    }

    @Override
    public ChessAlliance getAlliance() {
        return pieceColor;
    }

    @Override
    public CellPosition getPos() {
        return pos;
    }

    @Override
    public List<Move> getCandidateMoves() {
        return candidateMoves;
    }

    @Override
    public void redoMove(Move move) {

    }
}
