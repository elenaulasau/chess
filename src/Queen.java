public class Queen extends Piece {
    public Queen(Color color) {
        super('♕', color);
    }

    @Override
    public movementLogic[] getMovements() {
        movementLogic[] res = {
                new leftDownDiagonal(),
                new leftUpDiagonal(),
                new rightUpDiagonal(),
                new rightDownDiagonal(),
                new downMovement(),
                new upMovement(),
                new leftMovement(),
                new rightMovement()
        };
        return res;
    }

    @Override
    public Piece copy() {
        return copyMoved(new Queen(getColor()));
    }

}
