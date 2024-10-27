public class PlayerMove {
    private Position from;
    private Position to;

    public static PlayerMove ShortCast = new PlayerMove(new Position(-1, -1), new Position(0, 0));
    public static PlayerMove LongCast = new PlayerMove(new Position(0, 0), new Position(-1, -1));

    public PlayerMove(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}
