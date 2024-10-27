public abstract class Piece {
    private Color color;
    private char figure;
    boolean isNotMoved = true;
    int numberOfMoves = 0;


    public Piece(char figure, Color color) {
        this.color = color;
        this.figure = (char) (figure + 6);
    }

    public boolean isMoved() {
        return !isNotMoved;
    }

    public void move() {
        isNotMoved = false;
        numberOfMoves++;
    }

    public movementLogic[] getMovements() {
        return new movementLogic[0];
    }

    public char getFigure() {
        return figure;
    }

    public boolean checkMove(Position pos) {
        return true;
    }

//    public char figColor(){
//        if(color == Color.WHITE) figure = (char) (figure+6);
//        return figure;
//    }

    public Color getColor() {
        return color;
    }

    public abstract Piece copy();

    protected Piece copyMoved(Piece piece) {
        piece.isNotMoved = isNotMoved;
        return piece;
    }

    @Override
    public String toString() {
        if (color == Color.WHITE)
            return "\u001B[31m" + figure;
        else return "\u001B[36m" + figure;
    }
}
