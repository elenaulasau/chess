import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.*;


//VISUAL PART END

public class Main {
    public static void main(String[] args) {

        System.setOut(new PrintStream(
                new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        Board board = new Board();
        Game game = new Game(board, new Scanner(System.in));
        game.defaultStart();
        game.start();
    }
}


