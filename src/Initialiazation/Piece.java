package Initialiazation;


public class Piece {
    private String name;
    private Position position;
    private Player player;

    public Piece(){}

    public Piece(String name,Player player){
        this.name=name;
        this.player=player;
    }
    public Piece(String name, Position position, Player player) {
        this.name = name;
        this.position = position;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Piece getPiece(String name){
        if(name.equals(this.name)){
            return this;
        }
        else{
            return null;
        }
    }
}
