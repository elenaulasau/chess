public class Bishop extends Piece {
    public Bishop(Color color) {
        super('♗', color);
    }

    @Override
    public movementLogic[] getMovements() {
        movementLogic[] res = {
                new leftDownDiagonal(),
                new leftUpDiagonal(),
                new rightUpDiagonal(),
                new rightDownDiagonal()
        };
        return res;
    }

    @Override
    public Piece copy() {
        return copyMoved(new Bishop(getColor()));
    }
}
