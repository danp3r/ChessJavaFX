import com.chess.board.BoardModel;
import com.chess.pieces.Figure;
import com.chess.pieces.Pawn;
import com.chess.pieces.Player;
import com.chess.pieces.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnTests {
    public static BoardModel model = new BoardModel();

    @Test
    public void testPawnPushUpdatesLocation() {
        Figure pawn = new Pawn(6, 4, Player.WHITE);
        assertTrue(model.pieceAt(6, 4) instanceof Pawn);
        assertTrue(model.pieceAt(4, 4).isEmpty());
        model.movePiece(pawn, 4, 4);
        assertTrue(model.pieceAt(4, 4) instanceof Pawn);
    }

    @Test
    public void testPawnPromotion() {
        Figure pawn = new Pawn(1, 4, Player.WHITE);
        model.movePiece(pawn, 0, 4);
        assertTrue(model.pieceAt(0, 4) instanceof Queen);
    }
}
