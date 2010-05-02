package no.kodedojo.sudoko;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SudokuTest {

    @Test
    public void shouldFindSolutionToFilledBoard() {
        Board board = mock(Board.class);
        when(board.isFilled(anyInt(), anyInt()))
                .thenReturn(true);

        Solver solver = new Solver();

        assertThat(solver.findSolution(board)).isTrue();
    }
}
