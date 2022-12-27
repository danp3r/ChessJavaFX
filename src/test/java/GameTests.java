import com.chess.board.BoardModel;
import com.chess.board.Game;
import com.chess.board.GameState;
import com.chess.pieces.Figure;
import com.chess.pieces.IllegalPlayerException;
import com.chess.pieces.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {
    public static Game game = new Game();

    private static void addMovesHelper(int[][] moves) {
        BoardModel model = game.getModel();
        for (int[] row : moves) {
            Figure figure = model.pieceAt(row[2], row[3]);
            game.addMove(row[0], row[1], figure);
        }
    }

    public static class GameValidation {
        @Test
        public void testBlackMovingFirstIsInvalid() {
            assertThrows(IllegalPlayerException.class, () -> {
                game.reset();
                int[][] gameMoves = {
                        {3, 4, 1, 4},
                        {4, 6, 6, 6},
                        {4, 7, 0, 3},
                };
                addMovesHelper(gameMoves);
            });
        }

        @Test()
        public void testWhiteMovingTwiceIsInvalid() {
            assertThrows(IllegalPlayerException.class, () -> {
                game.reset();
                int[][] gameMoves = {
                        {5, 5, 6, 5},
                        {4, 6, 6, 6},
                        {4, 7, 0, 3},
                };
                addMovesHelper(gameMoves);
            });
        }
    }
}