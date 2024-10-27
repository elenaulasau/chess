//VISUAL PART BEGIN
public class Printer {
    private Board board;

    public Printer() {
    }

    public void printBoard(Board board) {
        for (int i = board.getBoard().length - 1; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                //System.out.print("[x"+j + "-y"+i+"]");

                System.out.print(board.getBoard()[j][i].display());
            }
            if (i == 4) System.out.print("  Defeated -    " + board.getDefeated() + "\u001B[0m");
            System.out.println();
        }
        System.out.println("    a    b   c   d " +
                "   e   f    g   h");
    }
}
