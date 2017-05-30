package com.sky.OOP.meteor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sky.OOP.meteor.view.LightBoardCopy;

/**
 * http://www.ibm.com/developerworks/cn/java/j-javaoptimization/index.html
 */
public class Test {
    private final Board mBoard;
    private final List<Piece> mPieces;
    private int numberOfSolutions;

    public Test() {
        boolean b = new File("JavaExercise/build/solutions").mkdirs();
        if (b) {
            System.out.println("Create solution folder");
        }
        mBoard = new Board();
        mPieces = new ArrayList<>(Board.CELL_COUNT / Piece.CELL_COUNT);

        Cell cell[] = new Cell[50];
        for (int i = 0; i < cell.length; i++) {
            cell[i] = new Cell();
        }

        // first piece
        cell[0].setNeighbour(Cell.EAST, cell[1]);
        cell[1].setNeighbour(Cell.WEST, cell[0]);
        cell[1].setNeighbour(Cell.EAST, cell[2]);
        cell[2].setNeighbour(Cell.WEST, cell[1]);
        cell[2].setNeighbour(Cell.EAST, cell[3]);
        cell[3].setNeighbour(Cell.WEST, cell[2]);
        cell[3].setNeighbour(Cell.SOUTH_EAST, cell[4]);
        cell[4].setNeighbour(Cell.NORTH_WEST, cell[3]);

        // second piece
        cell[5].setNeighbour(Cell.SOUTH_EAST, cell[6]);
        cell[6].setNeighbour(Cell.NORTH_WEST, cell[5]);
        cell[6].setNeighbour(Cell.SOUTH_WEST, cell[7]);
        cell[7].setNeighbour(Cell.NORTH_EAST, cell[6]);
        cell[7].setNeighbour(Cell.WEST, cell[8]);
        cell[8].setNeighbour(Cell.EAST, cell[7]);
        cell[8].setNeighbour(Cell.SOUTH_WEST, cell[9]);
        cell[9].setNeighbour(Cell.NORTH_EAST, cell[8]);

        // third piece
        cell[10].setNeighbour(Cell.WEST, cell[11]);
        cell[11].setNeighbour(Cell.EAST, cell[10]);
        cell[11].setNeighbour(Cell.SOUTH_WEST, cell[12]);
        cell[12].setNeighbour(Cell.NORTH_EAST, cell[11]);
        cell[12].setNeighbour(Cell.SOUTH_EAST, cell[13]);
        cell[13].setNeighbour(Cell.NORTH_WEST, cell[12]);
        cell[13].setNeighbour(Cell.SOUTH_EAST, cell[14]);
        cell[14].setNeighbour(Cell.NORTH_WEST, cell[13]);

        // fourth piece
        cell[15].setNeighbour(Cell.SOUTH_WEST, cell[16]);
        cell[16].setNeighbour(Cell.NORTH_EAST, cell[15]);
        cell[16].setNeighbour(Cell.WEST, cell[17]);
        cell[17].setNeighbour(Cell.EAST, cell[16]);
        cell[16].setNeighbour(Cell.SOUTH_WEST, cell[18]);
        cell[18].setNeighbour(Cell.NORTH_EAST, cell[16]);
        cell[17].setNeighbour(Cell.SOUTH_EAST, cell[18]);
        cell[18].setNeighbour(Cell.NORTH_WEST, cell[17]);
        cell[18].setNeighbour(Cell.SOUTH_EAST, cell[19]);
        cell[19].setNeighbour(Cell.NORTH_WEST, cell[18]);

        // fifth piece
        cell[20].setNeighbour(Cell.SOUTH_EAST, cell[21]);
        cell[21].setNeighbour(Cell.NORTH_WEST, cell[20]);
        cell[21].setNeighbour(Cell.SOUTH_WEST, cell[22]);
        cell[22].setNeighbour(Cell.NORTH_EAST, cell[21]);
        cell[21].setNeighbour(Cell.EAST, cell[23]);
        cell[23].setNeighbour(Cell.WEST, cell[21]);
        cell[23].setNeighbour(Cell.SOUTH_EAST, cell[24]);
        cell[24].setNeighbour(Cell.NORTH_WEST, cell[23]);

        // sixt piece
        cell[25].setNeighbour(Cell.SOUTH_WEST, cell[26]);
        cell[26].setNeighbour(Cell.NORTH_EAST, cell[25]);
        cell[25].setNeighbour(Cell.SOUTH_EAST, cell[27]);
        cell[27].setNeighbour(Cell.NORTH_WEST, cell[25]);
        cell[26].setNeighbour(Cell.SOUTH_EAST, cell[28]);
        cell[28].setNeighbour(Cell.NORTH_WEST, cell[26]);
        cell[27].setNeighbour(Cell.SOUTH_WEST, cell[28]);
        cell[28].setNeighbour(Cell.NORTH_EAST, cell[27]);
        cell[28].setNeighbour(Cell.SOUTH_WEST, cell[29]);
        cell[29].setNeighbour(Cell.NORTH_EAST, cell[28]);

        // seventh piece
        cell[30].setNeighbour(Cell.SOUTH_WEST, cell[31]);
        cell[31].setNeighbour(Cell.NORTH_EAST, cell[30]);
        cell[32].setNeighbour(Cell.SOUTH_EAST, cell[31]);
        cell[31].setNeighbour(Cell.NORTH_WEST, cell[32]);
        cell[31].setNeighbour(Cell.SOUTH_EAST, cell[33]);
        cell[33].setNeighbour(Cell.NORTH_WEST, cell[31]);
        cell[33].setNeighbour(Cell.SOUTH_WEST, cell[34]);
        cell[34].setNeighbour(Cell.NORTH_EAST, cell[33]);

        // eigth piece
        cell[35].setNeighbour(Cell.SOUTH_EAST, cell[36]);
        cell[36].setNeighbour(Cell.NORTH_WEST, cell[35]);
        cell[35].setNeighbour(Cell.SOUTH_WEST, cell[37]);
        cell[37].setNeighbour(Cell.NORTH_EAST, cell[35]);
        cell[37].setNeighbour(Cell.SOUTH_WEST, cell[38]);
        cell[38].setNeighbour(Cell.NORTH_EAST, cell[37]);
        cell[38].setNeighbour(Cell.SOUTH_EAST, cell[39]);
        cell[39].setNeighbour(Cell.NORTH_WEST, cell[38]);

        // ninth piece
        cell[40].setNeighbour(Cell.EAST, cell[41]);
        cell[41].setNeighbour(Cell.WEST, cell[40]);
        cell[41].setNeighbour(Cell.EAST, cell[42]);
        cell[42].setNeighbour(Cell.WEST, cell[41]);
        cell[42].setNeighbour(Cell.NORTH_EAST, cell[43]);
        cell[43].setNeighbour(Cell.SOUTH_WEST, cell[42]);
        cell[43].setNeighbour(Cell.EAST, cell[44]);
        cell[44].setNeighbour(Cell.WEST, cell[43]);

        // tenth piece
        cell[45].setNeighbour(Cell.EAST, cell[46]);
        cell[46].setNeighbour(Cell.WEST, cell[45]);
        cell[46].setNeighbour(Cell.EAST, cell[47]);
        cell[47].setNeighbour(Cell.WEST, cell[46]);
        cell[47].setNeighbour(Cell.NORTH_EAST, cell[48]);
        cell[48].setNeighbour(Cell.SOUTH_WEST, cell[47]);
        cell[47].setNeighbour(Cell.EAST, cell[49]);
        cell[49].setNeighbour(Cell.WEST, cell[47]);
        cell[49].setNeighbour(Cell.NORTH_WEST, cell[48]);
        cell[48].setNeighbour(Cell.SOUTH_EAST, cell[49]);

        Cell[] cells;
        for (int i = 0; i < 10; i++) {
            cells = new Cell[5];
            for (int j = 0; j < 5; j++) {
                cells[j] = cell[(i * 5) + j];
            }
            Piece newPiece = new Piece(cells, i);
            //newPiece.generateAllPermutations(board);
            mPieces.add(newPiece);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting " + Test.class.getName() + "  " + new Date());
        long time = System.currentTimeMillis();

        Test solver = new Test();
        solver.resolve();

        System.out.println("Stopping " + Test.class.getName() + "  " + new Date());
        System.out.println(solver.numberOfSolutions + " solutions found.");
        System.out.println("Run time: " + (System.currentTimeMillis() - time));
    }

    public void resolve() {
        if (!mPieces.isEmpty()) {
            // We'll try to find a piece that fits on this board cell
            int emptyBoardCellIdx = mBoard.getFirstEmptyCellIndex();
            // Try all available pieces
            for (int h = 0; h < mPieces.size(); h++) {
                Piece currentPiece = mPieces.remove(h);
                for (int i = 0; i < Piece.PERMUTATION_COUNT; i++) {
                    Piece permutation = currentPiece.nextMutation();

                    /* Instead of always using the first cell to manipulate
                       the piece, we now try to fit any cell of the piece on
                       the first empty board cell */
                    for (int j = 0; j < Piece.CELL_COUNT; j++) {
                        if (mBoard.placePiece(permutation, j, emptyBoardCellIdx)) {
                            if (!prune()) resolve();
                            mBoard.removePiece(permutation);
                        }
                    }
                }

                /* Put the piece back into the list at the position where
                   we took it to maintain the order of the list */
                mPieces.add(h, currentPiece);
            }
        } else {
            puzzleSolved();
        }
    }

    private boolean prune() {
        mBoard.unProcessing();
        for (int i = 0; i < Board.CELL_COUNT; i++) {
            BoardCell boardCell = mBoard.getCell(i);
            if (boardCell.getPiece() == null && !boardCell.isProcessing()
                && boardCell.getIslandCount() % Piece.CELL_COUNT != 0) {
                return true;
            }
        }
        return false;
    }

    private void puzzleSolved() {
        // Print out the solution number and time.
        numberOfSolutions++;
        System.out.println(numberOfSolutions + " - " + new Date());

        // Serialize the found solution.
        String fileName = "solutions/" + numberOfSolutions + ".ser";

        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(file));
            //ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(new LightBoardCopy(mBoard));
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
