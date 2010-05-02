package no.kodedojo.sudoko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Board {
    private static final int BOX_SIZE = 3;
    private static final int BOARD_SIZE = BOX_SIZE*BOX_SIZE;
    private int[][] cells = new int[BOARD_SIZE][BOARD_SIZE];

    public boolean isFilled(int row, int column) {
        return cells[row][column] != 0;
    }

    public void setCellValue(int row, int column, int value) {
        cells[row][column] = value;
    }

    public void clearCell(int row, int column) {
        cells[row][column] = 0;
    }

    public Collection<Integer> getOptionsForCell(int row, int column) {
        List<Integer> result = allOptions();
        removeOptionsInRow(result, row);
        removeOptionsInColumn(result, column);
        removeOptionsInBox(result, row, column);
        return result;
    }

    private ArrayList<Integer> allOptions() {
        return new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    private void removeOptionsInRow(List<Integer> result, int row) {
        for (int column=0; column< BOARD_SIZE; column++ ) {
            removeValueInCell(result, row, column);
        }
    }

    private void removeOptionsInColumn(List<Integer> result, int column) {
        for (int row=0; row< BOARD_SIZE; row++ ) {
            removeValueInCell(result, row, column);
        }
    }

    private void removeOptionsInBox(List<Integer> result, int targetRow, int targetColumn) {
        int boxRow = targetRow - (targetRow% BOX_SIZE);
        int boxColumn = targetColumn - (targetColumn% BOX_SIZE);
        for (int row=boxRow; row<boxRow+ BOX_SIZE; row++) {
            for (int column=boxColumn; column<boxColumn + BOX_SIZE; column++ ) {
                removeValueInCell(result, row, column);
            }
        }
    }

    private void removeValueInCell(List<Integer> result, int row, int column) {
        result.remove((Integer)cells[row][column]);
    }

    public int getValueInCell(int row, int column) {
        return cells[row][column];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                if (isFilled(rowIndex, columnIndex)) {
                    result.append(getValueInCell(rowIndex, columnIndex));
                } else {
                    result.append(" ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public void readBoard(String boardAsString) {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                int cellPosition = rowIndex*BOARD_SIZE + columnIndex;
                String cellValue = boardAsString.substring(cellPosition, cellPosition + 1);
                if (cellValue.equals(".")) {
                    clearCell(rowIndex, columnIndex);
                } else {
                    setCellValue(rowIndex, columnIndex, Integer.parseInt(cellValue));
                }
            }
        }
    }
}
