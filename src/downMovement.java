public class downMovement implements movementLogic {
    @Override
    public int changeX(int x) {
        return x;
    }

    @Override
    public int changeY(int y) {
        return y - 1;
    }
}
