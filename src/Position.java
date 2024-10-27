public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public static Position convert(String s) {
        int x;
        String[] characters = s.split("");
        if (characters.length != 2)
            return null;

        switch (characters[0].toLowerCase()) {
            case "a":
                x = 0;
                break;
            case "b":
                x = 1;
                break;
            case "c":
                x = 2;
                break;
            case "d":
                x = 3;
                break;
            case "e":
                x = 4;
                break;
            case "f":
                x = 5;
                break;
            case "g":
                x = 6;
                break;
            case "h":
                x = 7;
                break;
            default:
                return null;
        }
        int y = Integer.parseInt(characters[1]) - 1;
        if (y < 0 || y > 7) return null;
        return new Position(x, y);
    }

    private int[] translate(String s) { // [x,y]
        int[] res = new int[2];

        return res;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
