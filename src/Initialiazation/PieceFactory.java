package Initialiazation;

import java.util.ArrayList;
import java.util.List;

public class PieceFactory {
    // 存储所有初始位置信息的映射
    private static final String[][] RED_INITIAL_SETUP = {
            {"车", "马", "相", "仕", "帅", "仕", "相", "马", "车"}, // y=0
            {"", "", "", "", "", "", "", "", ""},                 // y=1 (空行)
            {"", "炮", "", "", "", "", "", "炮", ""},             // y=2
            {"兵", "", "兵", "", "兵", "", "兵", "", "兵"},       // y=3
            {"", "", "", "", "", "", "", "", ""},                 // y=4 (空行)
    };

    private static final String[][] BLACK_INITIAL_SETUP = {
            {"", "", "", "", "", "", "", "", ""},                 // y=5 (空行)
            {"卒", "", "卒", "", "卒", "", "卒", "", "卒"},       // y=6
            {"", "炮", "", "", "", "", "", "炮", ""},             // y=7
            {"", "", "", "", "", "", "", "", ""},                 // y=8 (空行)
            {"車", "馬", "象", "士", "將", "士", "象", "馬", "車"}, // y=9
    };

    private Piece createSpecificPiece(String pieceName, int x, int y, Player player) {
        Position pos = new Position(x, y);

        switch (pieceName) {
            case "帅":
            case "將":
                return new King(pos, player);
            case "仕":
            case "士":
                return new Advisor(pos, player);
            case "相":
            case "象":
                return new Elephant(pos, player);
            case "马":
            case "馬":
                return new Horse(pos, player);
            case "车":
            case "車":
                return new Rook(pos, player);
            case "炮":
                return new Cannon(pos, player);
            case "兵":
            case "卒":
                return new Soldier(pos, player);
        }
        throw new IllegalArgumentException("未知的棋子类型: " + pieceName);

    }


    public List<Piece> RedPiecesI(){
        List<Piece> RedPiecesI = new ArrayList<>();
        for(int y=0;y<5;y++){
            for(int x=0;x<9;x++){
                String s = RED_INITIAL_SETUP[y][x];
                if(s!=null && !s.equals("")){
                    RedPiecesI.add(createSpecificPiece(s, x, y, Player.RED));
                }
            }
        }
        return RedPiecesI;
    }

    public List<Piece> BlackPiecesI(){
        List<Piece> BlackPiecesI = new ArrayList<>();
        for(int y=0;y<5;y++){
            for(int x=0;x<9;x++){
                String s = BLACK_INITIAL_SETUP[y][x];
                if(s!=null && !s.equals("")){
                    BlackPiecesI.add(createSpecificPiece(s, x, y+5, Player.BLACK));
                }
            }
        }
        return BlackPiecesI;
    }

    public List<Piece> PiecesI(){
        List<Piece> PiecesI = new ArrayList<>();
        PiecesI.addAll(RedPiecesI());
        PiecesI.addAll(BlackPiecesI());
        return PiecesI;
    }




}
