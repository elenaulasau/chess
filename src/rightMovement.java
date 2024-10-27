public class rightMovement implements movementLogic {
    @Override
    public int changeX(int x) {
        return x + 1;
    }

    @Override
    public int changeY(int y) {
        return y;
    }
}
