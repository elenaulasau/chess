public class Rook extends Piece {
    public Rook(Color color) {
        super('♖', color);
    }

    @Override
    public movementLogic[] getMovements() {
        movementLogic[] res = {new downMovement(), new upMovement(), new leftMovement(), new rightMovement()};
        return res;
    }

    @Override
    public Piece copy() {
        return copyMoved(new Rook(getColor()));
    }
}
