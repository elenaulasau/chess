public class kingMovement implements movementLogic {
    private int deltaX;
    private int deltaY;
    private boolean isFirstMove = true;

    public kingMovement(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public int changeX(int x) {
        if (isFirstMove) {
            isFirstMove = false;
            return x + deltaX;
        }
        return -1;
    }

    @Override
    public int changeY(int y) {
        return y + deltaY;
    }
}
