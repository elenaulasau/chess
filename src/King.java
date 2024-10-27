public class King extends Piece {
    public King(Color color) {
        super('♔', color);
    }

    @Override
    public movementLogic[] getMovements() {
        movementLogic[] res = {
                new kingMovement(1, 1),
                new kingMovement(1, 0),
                new kingMovement(1, -1),
                new kingMovement(0, 1),
                new kingMovement(0, 0),
                new kingMovement(0, -1),
                new kingMovement(-1, 1),
                new kingMovement(-1, 0),
                new kingMovement(-1, -1),
        };
        return res;
    }

    @Override
    public Piece copy() {
        return copyMoved(new King(getColor()));
    }
}
