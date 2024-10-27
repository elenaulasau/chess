public class Knight extends Piece {
    public Knight(Color color) {
        super('♘', color);
    }

    @Override
    public movementLogic[] getMovements() {
        movementLogic[] res = {
                new knightMovement(-2, 1),
                new knightMovement(-1, 2),
                new knightMovement(1, 2),
                new knightMovement(2, 1),
                new knightMovement(2, -1),
                new knightMovement(1, -2),
                new knightMovement(-1, -2),
                new knightMovement(-2, -1),
        };
        return res;
    }

    @Override
    public Piece copy() {
        return copyMoved(new Knight(getColor()));
    }
}
