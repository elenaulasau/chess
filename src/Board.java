import java.util.ArrayList;

public class Board {
    private ArrayList<Piece> defeated = new ArrayList<Piece>();
    private Square[][] board = new Square[8][8];

    public Board() {
        fillEmpty();
    }

    public void fillEmpty() {
        String col;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    if ((i + j) % 2 == 0) col = "\033[47m";
                    else col = "\033[40m";
                    board[i][j] = new Square(col);
                }
            }
        }
    }


//    public void printBoard(){
//        for(int i = board.length-1; i >= 0; i--){
//            System.out.print(i+1 + " ");
//            for(int j = 0; j < board[i].length; j++){
//                //System.out.print("[x"+j + "-y"+i+"]");
//
//                System.out.print(board[j][i].display());
//            }
//            System.out.println();
//        }
//        System.out.println("    a    b   c   d " +
//                "   e   f    g   h");
//    }

//    public Square getSquare(int[] pos){
//        return board[pos[0]][pos[1]];
//    }

    public Board copy() {
        Board Board1 = new Board();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j].getPiece();
                if (piece != null)
                    Board1.board[i][j].setPiece(piece.copy());
            }
        }
        Board1.defeated.addAll(defeated);


        return Board1;
    }

    public Square[][] getBoard() {
        return board;
    }

    public boolean checkIsLegalMove(PlayerMove playerMove, boolean isWhiteTurn, boolean skipCheckCheck) {
        if (playerMove.getFrom() == null || playerMove.getTo() == null) return false;
        Square from = getSquare(playerMove.getFrom());
        Square to = getSquare(playerMove.getTo());
        if (from.isEmpty()) return false;
        Piece pieceFrom = from.getPiece();
        Piece pieceTo1 = to.getPiece();
        //castling
//        if(pieceFrom instanceof King && pieceTo1 instanceof Rook && !pieceTo1.isMoved() && !pieceFrom.isMoved()){
//
//        }

        //en passant
        if (pieceFrom instanceof Pawn && pieceTo1 instanceof Pawn && pieceTo1.numberOfMoves == 1) {

        }


        if (isWhiteTurn && (pieceFrom.getColor() == Color.BLACK)) return false;
        if (!isWhiteTurn && (pieceFrom.getColor() == Color.WHITE)) return false;
        Piece pieceTo = getSquare(playerMove.getTo()).getPiece();
        if (pieceTo != null) {
            if (pieceTo.getColor() == pieceFrom.getColor()) return false;
        }

        //check is Check
        //ONLY FOR KING MOVEMENT
        if (!skipCheckCheck && pieceFrom instanceof King) {
            if (checkIsCheck(playerMove.getFrom(), playerMove.getTo(), pieceFrom.getColor())) return false;
        }
        if (pieceFrom instanceof Pawn) {
            return checkPawnMove(playerMove, pieceFrom, pieceTo);
        }
        return checkMovements(pieceFrom.getMovements(), playerMove);
    }

    public boolean IsCheck(Color color) {
        Position kingPosition = findKingPosition(color);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = board[i][j].getPiece();
                if (current != null && current.getColor() != color
                        && checkIsLegalMove(new PlayerMove(new Position(i, j), kingPosition), color != Color.WHITE, true))
                    return true;

            }
        }
        return false;
    }

    public Position findKingPosition(Color color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece current = board[i][j].getPiece();
                if (current instanceof King && current.getColor() == color) return new Position(i, j);
            }
        }
        return new Position(-1, -1);
    }

    private boolean checkPawnMove(PlayerMove playerMove, Piece pieceFrom, Piece pieceTo) {
        int xFrom = playerMove.getFrom().getX();
        int yFrom = playerMove.getFrom().getY();
        int xTo = playerMove.getTo().getX();
        int yTo = playerMove.getTo().getY();

        //WHITE PAWNS
        if (pieceFrom.getColor() == Color.WHITE &&
                pieceTo != null && ((xTo == xFrom + 1 || xTo == xFrom - 1) && (yTo == yFrom + 1))) return true;
        else if (pieceTo == null) {
            if (pieceFrom.getColor() == Color.WHITE &&
                    !pieceFrom.isMoved() && xTo == xFrom &&
                    (yTo == yFrom + 1 || yTo == yFrom + 2)) return true;
            else if (pieceFrom.getColor() == Color.WHITE
                    && pieceFrom.isMoved()) {


                if (yTo == yFrom + 1) return true;
            }


        }
        //BLACK PAWNS
        if (pieceFrom.getColor() == Color.BLACK &&
                pieceTo != null && (((xTo == xFrom - 1 || xTo == xFrom + 1) && (yTo == yFrom - 1)))) return true;
        else if (pieceTo == null) {
            if (pieceFrom.getColor() == Color.BLACK &&
                    !pieceFrom.isMoved() && (xTo == xFrom) &&
                    (yTo == yFrom - 1 || yTo == yFrom - 2)) return true;
            else if (pieceFrom.getColor() == Color.BLACK
                    && pieceFrom.isMoved())
                if (yTo == yFrom - 1) return true;

        }

        return false;
    }

    public boolean checkIsCheck(Position kingPosition, Position to, Color color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == kingPosition.getX() && j == kingPosition.getY()) continue;
                Piece current = board[i][j].getPiece();
                Position from = new Position(i, j);
                if (!board[i][j].isEmpty() && current.getColor() != color) {
                    if (checkIsLegalMove(new PlayerMove(from, to), color != Color.WHITE, true)) return true;

                }

            }

        }
        return false;
    }

    public boolean checkMovements(movementLogic[] ml, PlayerMove playerMove) {
        for (int i = 0; i < ml.length; i++) {
            if (movementLogic.check(ml[i], this, playerMove)) {
//                int x = playerMove.getTo().getX();
//                int y = playerMove.getTo().getY();
////                if(board[x][y].getPiece() != null){
////                     defeated.add( board[x][y].getPiece());
////                }
                return true;
            }

        }
        return false;
    }

    public Square getSquare(Position position) {
        return board[position.getX()][position.getY()];
    }

    private void setSquare(Position position, Square square) {
        board[position.getX()][position.getY()] = square;
    }

    public void moveFigure(PlayerMove move, Color color) {
        if (move == PlayerMove.ShortCast) {
            cast(color, true);
            return;
        }

        if (move == PlayerMove.LongCast) {
            cast(color, false);
            return;
        }

        Square squareFrom = getSquare(move.getFrom());
        Square squareTo = getSquare(move.getTo());
        Piece piece = squareFrom.getPiece();
        if (piece != null) {
            piece.move();
        }

        Piece defeatedPiece = squareTo.getPiece();
        if (defeatedPiece != null) {
            defeated.add(defeatedPiece);
        }

        squareTo.setPiece(piece);
        squareFrom.setPiece(null);
    }

    public ArrayList<Piece> getDefeated() {
        return defeated;
    }

    public void addDefeated(Piece piece) {
        defeated.add(piece);
    }

    public void initPiece(Piece piece, String posStr) {
        getSquare(Position.convert(posStr)).setPiece(piece);
    }

    public void initPiece(Piece piece, int x, int y) {
        getSquare(new Position(x, y)).setPiece(piece);
    }

    public void cast(Color color, boolean isShortCast) {
        int y = color == Color.WHITE ? 0 : 7;
        if (isShortCast) {
            board[6][y].setPiece(board[4][y].getPiece());
            board[6][y].getPiece().move();
            board[4][y].setPiece(null);
            board[5][y].setPiece(board[7][y].getPiece());
            board[5][y].getPiece().move();
            board[7][y].setPiece(null);
        } else {
            board[2][y].setPiece(board[4][y].getPiece());
            board[2][y].getPiece().move();
            board[4][y].setPiece(null);
            board[3][y].setPiece(board[0][y].getPiece());
            board[3][y].getPiece().move();
            board[0][y].setPiece(null);
        }
    }

    public boolean checkShortCastling(Color color) {
        int y = color == Color.WHITE ? 0 : 7;
        Piece king = board[4][y].getPiece();
        if (king.isMoved()) return false;
        if (!(king instanceof King)) return false;

        Piece rook = board[7][y].getPiece();
        if (rook.isMoved()) return false;
        if (!(rook instanceof Rook)) return false;
        if (!board[5][y].isEmpty() || !board[6][y].isEmpty()) return false;
        return true;
    }

    public boolean checkLongCastling(Color color) {
        int y = color == Color.WHITE ? 0 : 7;
        Piece king = board[4][y].getPiece();
        if (king.isMoved()) return false;
        if (!(king instanceof King)) return false;

        Piece rook = board[0][y].getPiece();
        if (rook.isMoved()) return false;
        if (!(rook instanceof Rook)) return false;
        if (!board[1][y].isEmpty() || !board[2][y].isEmpty() || !board[3][y].isEmpty()) return false;
        return true;
    }
}
