public interface movementLogic {
    public static boolean check(movementLogic logic, Board board, PlayerMove playermove) {
        int x = playermove.getFrom().getX();
        int y = playermove.getFrom().getY();
        int xTo = playermove.getTo().getX();
        int yTo = playermove.getTo().getY();
        Piece pieceFrom = board.getSquare(playermove.getFrom()).getPiece();

        x = logic.changeX(x);
        y = logic.changeY(y);
        while (x >= 0 && y >= 0 && y <= 7 && x <= 7) {

            Piece piece = board.getSquare(new Position(x, y)).getPiece();
            if (x == xTo && y == yTo) {
                if (piece == null)
                    return true;
                if (piece.getColor() != pieceFrom.getColor())
                    return true;

                return false;
            }
            if (piece != null)
                return false;

            x = logic.changeX(x);
            y = logic.changeY(y);
        }

        return false;
    }

    public int changeX(int x);

    public int changeY(int y);

}
