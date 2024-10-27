import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ReadingWriting {
    public boolean reading(String filename, Board board) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                return false;
            }
            FileInputStream fis = new FileInputStream(filename);
            read(fis, board);
            fis.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void writing(String filename, Board board) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            write(fos, board);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(FileInputStream fis, Board board) {
        try {
            byte[] all = fis.readAllBytes();
            if (all.length % 2 != 0) System.out.println("Wrong file");
            else {
                for (int i = 0; i < all.length; i += 2) {
                    byte[] piece = {all[i], all[i + 1]};
                    initPiece(piece, board);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initPiece(byte[] arr, Board board) {
        int p1 = ((int) arr[0]) << 8;
        int p0 = (int) arr[1];

        int p = p1 | (p0 & 0xFF);
        int figureType = ((p) & 0b00000111);
        int horizontal = ((p >> 3) & 0b00001111);
        int vertical = ((p >> 7) & 0b00001111);
        Color color = ((p >> 11) & 0b00000001) == 1 ? Color.WHITE : Color.BLACK;
        Piece piece = translatePiece(figureType, color);
        if (horizontal == 0 && vertical == 0)
            board.addDefeated(piece);
        else {
            board.initPiece(piece, horizontal - 1, 8 - vertical);
        }

    }

    private Piece translatePiece(int figureType, Color color) {
        if (figureType == 1) return new King(color);
        if (figureType == 2) return new Queen(color);
        if (figureType == 3) return new Rook(color);
        if (figureType == 4) return new Bishop(color);
        if (figureType == 5) return new Knight(color);
        if (figureType == 0) return new Pawn(color);
        return null;
    }

    //
    private void write(FileOutputStream fos, Board board) {
        try {
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard().length; j++) {
                    Piece current = board.getBoard()[i][j].getPiece();
                    if (current != null) fos.write(translatePiece(current, i + 1, 8 - j));
                }
            }

            for (int i = 0; i < board.getDefeated().size(); i++) {
                Piece current = board.getDefeated().get(i);
                fos.write(translatePiece(current, 0, 0));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public byte[] translatePiece(Piece piece, int x, int y) {
        byte figureType;
        byte horizontal = (byte) (x);
        byte vertical = (byte) (y);
        byte color = (byte) (piece.getColor() == Color.WHITE ? 1 : 0);
        if (piece instanceof King) figureType = 1;
        else if (piece instanceof Queen) figureType = 2;
        else if (piece instanceof Rook) figureType = 3;
        else if (piece instanceof Bishop) figureType = 4;
        else if (piece instanceof Knight) figureType = 5;
        else if (piece instanceof Pawn) figureType = 0;
        else return null;
        int p = (0b00000001 & color) << 11 | (0b00001111 & vertical) << 7 | (0b00001111 & horizontal) << 3 | (0b00000111 & figureType);
        return new byte[]{
                (byte) ((p >> 8) & 0xFF),
                (byte) (p & 0xFF)};
    }
}
