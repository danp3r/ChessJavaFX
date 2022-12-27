import com.chess.pieces.Bishop;
import com.chess.pieces.IllegalPieceException;
import com.chess.pieces.Player;
import org.junit.jupiter.api.Test;

import static com.chess.pieces.Player.BLACK;
import static com.chess.pieces.Player.UNDEFINED;
import static com.chess.pieces.Player.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BishopTests {
    private static Bishop createBishop(int rank, int file, Player player) {
        return new Bishop(rank, file, player);
    }

    public static class BishopCreation {
        @Test
        public void testBishopRankEightThrowsException() {
            assertThrows(IllegalPieceException.class, () -> createBishop(8, 2, WHITE));
        }
    
        @Test
        public void testBishopRankNegativeOneThrowsException() {
            assertThrows(IllegalPieceException.class, () -> createBishop(-1, 5, BLACK));
        }
    
        @Test
        public void testBishopFileEightThrowsException() {
            assertThrows(IllegalPieceException.class, () -> createBishop(5, 8, WHITE));
        }
    
        @Test
        public void testBishopFileNegativeTwoThrowsException() {
            assertThrows(IllegalPieceException.class, () -> createBishop(-2, 6, BLACK));
        }

        @Test
        public void testBishopUndefinedPlayerThrowsException() {
            assertThrows(IllegalPieceException.class, () -> createBishop(2, 5, UNDEFINED));
        }
    }

    public static class BishopLocation {
        @Test
        public void testWhiteBishopRankFiveFileFourShouldSucceed() {
            Bishop bishop = createBishop(5, 4, WHITE);
            assertSame(bishop.getPlayer(), WHITE);
            assertEquals(5, bishop.getRank());
            assertEquals(4, bishop.getFile());
        }

        @Test
        public void testBlackBishopRankThreeFileSixShouldSucceed() {
            Bishop bishop = createBishop(3, 6, BLACK);
            assertSame(bishop.getPlayer(), BLACK);
            assertEquals(3, bishop.getRank());
            assertEquals(6, bishop.getFile());
        }

        @Test
        public void testWhiteBishopSetRankToEightShouldFail() {
            assertThrows(IllegalPieceException.class, () -> {
                Bishop bishop = createBishop(4, 5, WHITE);
                bishop.setRank(8);
            });
        }

        @Test
        public void testBlackBishopSetRankToNegativeFiveShouldFail() {
            assertThrows(IllegalPieceException.class, () -> {
                Bishop bishop = createBishop(1, 6, BLACK);
                bishop.setRank(-5);
            });
        }

        @Test
        public void testWhiteBishopSetFileToEighteenShouldFail() {
            assertThrows(IllegalPieceException.class, () -> {
                Bishop bishop = createBishop(4, 3, WHITE);
                bishop.setFile(18);
            });
        }

        @Test
        public void testBlackBishopSetFileToNegativeOneShouldFail() {
            assertThrows(IllegalPieceException.class, () -> {
                Bishop bishop = createBishop(7, 0, WHITE);
                bishop.setFile(-1);
            });
        }
    }
}