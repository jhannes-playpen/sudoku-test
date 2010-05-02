package no.kodedojo.sudoko;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BoardTest {
    private Board board = new Board();

    @Test
    public void setCellValueShouldFillCell(){
        assertThat(board.isFilled(0, 0)).isFalse();
        board.setCellValue(0,0, 3);
        assertThat( board.isFilled(0, 0) ).isTrue();
        assertThat( board.isFilled(1, 1) ).isFalse();
    }

    @Test
    public void shouldAllowAllOptionsWhenBoardEmpty() throws Exception {
        assertThat(board.getOptionsForCell(1, 4))
                .containsOnly(1,2,3,4,5,6,7,8,9);
    }

    @Test
    public void shouldNotAllowDuplicatesInRow() throws Exception {
        int row = 4;
        board.setCellValue(row, 2, 5);
        board.setCellValue(row, 8, 3);

        assertThat(board.getOptionsForCell(row, 4)).excludes(3, 5);
    }

    @Test
    public void shouldNotAllowDuplicatesInColumn() throws Exception {
        int column = 4;
        board.setCellValue(2, column, 5);
        board.setCellValue(8, column, 3);

        assertThat(board.getOptionsForCell(4, column)).excludes(3, 5);
    }

    @Test
    public void shouldNotAllowDuplicatesInBox() throws Exception {
        board.setCellValue(3, 3, 5);
        board.setCellValue(5, 5, 6);
        board.setCellValue(3, 5, 7);

        assertThat(board.getOptionsForCell(4, 4)).excludes(5, 6, 7);
        assertThat(board.getOptionsForCell(8, 8)).contains(5, 6, 7);
    }

    @Test
    public void clearedCellsShouldNotBeFilled() throws Exception {
        board.setCellValue(1, 1, 5);
        board.clearCell(1, 1);
        assertThat(board.isFilled(1, 1)).isFalse();
    }

    @Test
    public void shouldParseBoard() throws Exception {
        StringBuilder boardAsText = repeat('.', 81);
        boardAsText.replace((0*9) + 0, (0*9)+0+1, "1");
        boardAsText.replace((8*9) + 0, (8*9)+0+1, "2");
        boardAsText.replace((8*9) + 8, (8*9)+8+1, "3");
        board.readBoard(boardAsText.toString());

        assertThat(board.isFilled(0, 1)).isFalse();
        assertThat(board.isFilled(0, 0)).isTrue();

        assertThat(board.getValueInCell(0, 0)).isEqualTo(1);
        assertThat(board.getValueInCell(8, 0)).isEqualTo(2);
        assertThat(board.getValueInCell(8, 8)).isEqualTo(3);
    }

    private StringBuilder repeat(char c, int repetitions) {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<repetitions; i++) result.append(c);
        return result;
    }

    @Test
    public void shouldPrintBoard() throws Exception {
        board.setCellValue(0,0, 1);
        board.setCellValue(0,8, 2);
        board.setCellValue(8,8, 3);

        String boardAsString = board.toString();
        String[] lines = boardAsString.split("\n");
        assertThat(lines).hasSize(9);
        assertThat(lines[1]).isEqualTo(repeat(' ', 9).toString());
        assertThat(lines[0]).startsWith("1").endsWith("2");
        assertThat(lines[8]).endsWith("3");
    }
}
