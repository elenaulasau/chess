public class Square {
    private Piece piece = null;
    private String reset = "\033[0m";
    private String color;

    public Square(String color, Piece piece) {
        this.color = color;
        this.piece = piece;
    }

    public Square(String color) {
        this.color = color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    public String getColor() {
        return color;
    }


    public boolean isEmpty() {
        return piece == null;
    }

    public String display() {
        if (isEmpty()) return (color + " " + " " + " " + " " + reset);
        else return color + " " + piece + " " + reset;
    }
}
