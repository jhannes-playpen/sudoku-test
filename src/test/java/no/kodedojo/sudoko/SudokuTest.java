package no.kodedojo.sudoko;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class SudokuTest {
    private Solver solver = new Solver();
    private Board board = mock(Board.class);

    @Before
    public void allCellsAreFilled() {
        when(board.isFilled(anyInt(), anyInt())).thenReturn(true);
    }

    @Test
    public void shouldFindSolutionToFilledBoard() {
        assertThat(solver.findSolution(board)).isTrue();
    }

    @Test
    public void shouldNotFindSolutionWhenACellHasNoOptions() throws Exception {
        when(board.isFilled(0, 0)).thenReturn(false);
        when(board.getOptionsForCell(0, 0)).thenReturn(noOptions());

        assertThat(solver.findSolution(board)).isFalse();
    }

    @Test
    public void shouldNotFindSolutionWhenLastCellHasNoOptions() throws Exception {
        when(board.isFilled(8, 8)).thenReturn(false);
        when(board.getOptionsForCell(8, 8)).thenReturn(noOptions());

        assertThat(solver.findSolution(board)).isFalse();
    }

    @Test
    public void shouldFindSingleSolution() throws Exception {
        int singleOption = 3;
        when(board.isFilled(0, 0)).thenReturn(false);
        when(board.getOptionsForCell(0, 0)).thenReturn(oneOption(singleOption));

        assertThat(solver.findSolution(board)).isTrue();

        verify(board).setCellValue(0,0, singleOption);
    }

    @Test
    public void shouldClearCellWhenDeadEnd() throws Exception {
        when(board.isFilled(0, 0)).thenReturn(false);
        when(board.getOptionsForCell(0, 0)).thenReturn(oneOption(3));
        when(board.isFilled(0, 1)).thenReturn(false);
        when(board.getOptionsForCell(0, 1)).thenReturn(noOptions());

        assertThat(solver.findSolution(board)).isFalse();

        verify(board).clearCell(0,1);
    }


    @Test
    public void shouldTryMoreOptionsWhenStuck() throws Exception {
        when(board.isFilled(0, 0)).thenReturn(false);
        int correctOptions = 6;
        when(board.getOptionsForCell(0, 0))
                .thenReturn(moreOptions(3, correctOptions));
        when(board.isFilled(0, 1)).thenReturn(false);
        when(board.getOptionsForCell(0, 1))
                .thenReturn(noOptions())
                .thenReturn(oneOption(3));

        assertThat(solver.findSolution(board)).isTrue();

        verify(board).setCellValue(0,0, correctOptions);
    }


    private List<Integer> moreOptions(Integer... options) {
        return Arrays.asList(options);
    }

    private List<Integer> oneOption(int singleOption) {
        return Arrays.asList(singleOption);
    }

    private List<Integer> noOptions() {
        return new ArrayList<Integer>();
    }
}
