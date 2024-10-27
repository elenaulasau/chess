public class Pawn extends Piece {
    public Pawn(Color color) {
        super('♙', color);
    }

    @Override
    public Piece copy() {
        return copyMoved(new Pawn(getColor()));
    }
}
