package no.kodedojo.sudoko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver {
    public boolean findSolution(Board board) {
        return findSolutionsRecursive(board, 0);
    }

    private boolean findSolutionsRecursive(Board board, int cellIndex) {
        if (cellIndex == 9*9) return true;

        int row = cellIndex/9;
        int column = cellIndex%9;
        if (board.isFilled(row, column)) {
            return findSolutionsRecursive(board, cellIndex+1);
        }

        for (Integer option : board.getOptionsForCell(row, column)) {
            board.setCellValue(row, column, option);

            if (findSolutionsRecursive(board, cellIndex + 1)){
                return true;
            }
        }

        board.clearCell(row, column);
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("puzzles.txt"));

        Solver solver = new Solver();
        String line;
        while ( (line=reader.readLine()) != null) {
            Board board = new Board();
            board.readBoard(line);
            solver.findSolution(board);
            System.out.println(board.toString());
        }
        reader.close();
    }
}
