import java.util.Scanner;

public class Game {
    private Board board;
    private Scanner scan;
    private boolean continueGame = true;

    private boolean isWhiteTurn = true;

    public Game(Board board, Scanner scan) {
        this.board = board;
        this.scan = scan;
    }

    public void start() {
        Printer printer = new Printer();

        while (continueGame) {
            System.out.println("Player can suggest draw by entering 'draw', load a game from a file - 'load', save game - 'save'");
            printer.printBoard(board);
            if (board.IsCheck(isWhiteTurn ? Color.WHITE : Color.BLACK)) {
                System.out.println("Check!!!");
                if (isCheckMate(isWhiteTurn ? Color.WHITE : Color.BLACK)) {
                    System.out.println("CHECKMATE");
                    continueGame = false;
                    continue;
                }
            }
            System.out.print(isWhiteTurn ? "White move - " : "Black move - ");
            Color color = isWhiteTurn ? Color.WHITE : Color.BLACK;

            PlayerMove move = null;
            while (move == null) {
                String nextMove = inputNextMove();
                if (nextMove.equals("draw")) {
                    System.out.println("Another Player suggested draw, do you want to accept it? yes/no");
                    String answ = inputNextMove();
                    if (answ.equals("no")) {
                        System.out.println("Continuing game...");
                        move = null;
                        continue;
                    } else if (answ.equals("yes")) {
                        System.out.println("Both agreed for a draw");
                        return;
                    } else {
                        System.out.println("Invalid answer, try again");
                        move = null;
                        continue;
                    }
                }

                if (nextMove.equals("save")) {
                    ReadingWriting rw = new ReadingWriting();
                    String fname = "src\\chess.bin";
                    rw.writing(fname, board);
                    System.out.println("Written successfully");
                    move = null;
                    continue;
                }

                if (nextMove.equals("load")) {
                    ReadingWriting rw = new ReadingWriting();
                    System.out.println("Enter file name with extension");
                    String name = scan.nextLine();
                    String fname = "src\\" + name;
                    Board loadedBoard = new Board();
                    if (rw.reading(fname, loadedBoard)) {
                        board = loadedBoard;
                        System.out.println("Reading successfully");
                        printer.printBoard(board);
                    } else {
                        System.out.println("Incorrect file");
                    }
                    move = null;
                    continue;
                }
                if (nextMove.equals("0-0")) {
                    if (board.checkShortCastling(isWhiteTurn ? Color.WHITE : Color.BLACK)) {
                        move = PlayerMove.ShortCast;
                    }

                } else if (nextMove.equals("0-0-0")) {
                    if (board.checkLongCastling(isWhiteTurn ? Color.WHITE : Color.BLACK)) {
                        move = PlayerMove.LongCast;
                    }
                } else {
                    move = translateLegalMove(nextMove, isWhiteTurn);
                }

                if (move == null)
                    System.out.println("Illegal move!");


                if (board.IsCheck(color)) {
                    Board copy = board.copy();
                    if (move != null) {
                        copy.moveFigure(move, color);
                        if (copy.IsCheck(color)) {
                            System.out.println("Still check - change move!");
                            move = null;
                        }
                    }
                }
            }
            board.moveFigure(move, color);

            if (!(move == PlayerMove.LongCast || move == PlayerMove.ShortCast)) {
                int yTo = move.getTo().getY();
                int xTo = move.getTo().getX();
                Square to = board.getBoard()[xTo][yTo];
                Piece piece = to.getPiece();
                if (piece instanceof Pawn) {
                    if (yTo == 7 || yTo == 0) {
                        to.setPiece(askForPiece(to, piece.getColor()));
                        board.addDefeated(piece);
                    }
                }
            }


            isWhiteTurn = !isWhiteTurn;

        }
        System.out.println(isWhiteTurn ? "Black won" : "White won");
    }

    private static Piece askForPiece(Square to, Color color) {
        System.out.println("WRITE IN LOWERCASE");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();

            switch (input) {
                case "queen":
                    return new Queen(color);
                case "rook":
                    return new Rook(color);
                case "bishop":
                    return new Bishop(color);
                case "knight":
                    return new Knight(color);
                default:
                    System.out.println("Wrong word inputed");
            }

            return null;
        }


    }

    public boolean checkNextCheck(PlayerMove move) {
        Board copy = board.copy();
        Color color = isWhiteTurn ? Color.WHITE : Color.BLACK;
        copy.moveFigure(move, color);
        if (copy.IsCheck(color)) {
            return true;
        }
        return false;
    }

    private PlayerMove translateLegalMove(String nextMove, boolean isWhiteTurn) {
        if (nextMove.length() < 4) return null;
        String pos1 = nextMove.substring(0, 2);
        String pos2 = nextMove.substring(nextMove.length() - 2);

        PlayerMove playerMove = new PlayerMove(Position.convert(pos1), Position.convert(pos2));


        if (!board.checkIsLegalMove(playerMove, isWhiteTurn, false) || checkNextCheck(playerMove))
            return null;

        return playerMove;
    }

    private String inputNextMove() {
        System.out.println("Input your move:");
        return scan.nextLine();
    }

    private boolean isCheckMate(Color color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece current = board.getBoard()[x][y].getPiece();
                if (current != null && current.getColor() == color) {
                    if (canPreventCheckMate(new Position(x, y), color))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean canPreventCheckMate(Position from, Color color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PlayerMove playerMove = new PlayerMove(from, new Position(x, y));
                if (board.checkIsLegalMove(playerMove, color == Color.WHITE, true)) {
                    Board copied = board.copy();
                    copied.moveFigure(playerMove, color);
                    if (!copied.IsCheck(color))
                        return true;
                }
            }
        }
        return false;
    }

    public void defaultStart() {
        board.initPiece(new Rook(Color.BLACK), "a8");
        board.initPiece(new Knight(Color.BLACK), "b8");
        board.initPiece(new Bishop(Color.BLACK), "c8");
        board.initPiece(new Queen(Color.BLACK), "d8");
        board.initPiece(new King(Color.BLACK), "e8");
        board.initPiece(new Bishop(Color.BLACK), "f8");
        board.initPiece(new Knight(Color.BLACK), "g8");
        board.initPiece(new Rook(Color.BLACK), "h8");

        board.initPiece(new Pawn(Color.BLACK), "a7");
        board.initPiece(new Pawn(Color.BLACK), "b7");
        board.initPiece(new Pawn(Color.BLACK), "c7");
        board.initPiece(new Pawn(Color.BLACK), "d7");
        board.initPiece(new Pawn(Color.BLACK), "e7");
        board.initPiece(new Pawn(Color.BLACK), "f7");
        board.initPiece(new Pawn(Color.BLACK), "g7");
        board.initPiece(new Pawn(Color.BLACK), "h7");


        board.initPiece(new Rook(Color.WHITE), "a1");
        board.initPiece(new Knight(Color.WHITE), "b1");
        board.initPiece(new Bishop(Color.WHITE), "c1");
        board.initPiece(new Queen(Color.WHITE), "d1");
        board.initPiece(new King(Color.WHITE), "e1");
        board.initPiece(new Bishop(Color.WHITE), "f1");
        board.initPiece(new Knight(Color.WHITE), "g1");
        board.initPiece(new Rook(Color.WHITE), "h1");

        board.initPiece(new Pawn(Color.WHITE), "a2");
        board.initPiece(new Pawn(Color.WHITE), "b2");
        board.initPiece(new Pawn(Color.WHITE), "c2");
        board.initPiece(new Pawn(Color.WHITE), "d2");
        board.initPiece(new Pawn(Color.WHITE), "e2");
        board.initPiece(new Pawn(Color.WHITE), "f2");
        board.initPiece(new Pawn(Color.WHITE), "g2");
        board.initPiece(new Pawn(Color.WHITE), "h2");
    }


}
