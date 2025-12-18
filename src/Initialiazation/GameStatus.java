package Initialiazation;

public enum GameStatus {
    // 进行中状态
    NORMAL("正常进行"),
    ABNORMAL("违规局面"),  // 新增

    // 将军状态
    RED_CHECK("红方被将军"),
    BLACK_CHECK("黑方被将军"),

    // 结束状态
    RED_CHECKMATE("红方被将死,黑方胜利"),
    BLACK_CHECKMATE("黑方被将死，红方胜利"),
    RED_STALEMATE("红方困毙，黑方胜利"),
    BLACK_STALEMATE("黑方困毙，红方胜利"),
    RED_WIN("红方胜利"),
    BLACK_WIN("黑方胜利"),
    DRAW("和棋");

    private final String description;

    GameStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGameOver() {
        return this == RED_WIN || this == BLACK_WIN || this == DRAW ||
                this == RED_CHECKMATE || this == BLACK_CHECKMATE ||
                this == RED_STALEMATE || this == BLACK_STALEMATE ||
                this == ABNORMAL;
    }

    public boolean isCheck() {
        return this == RED_CHECK || this == BLACK_CHECK;
    }

    public boolean isNormal() {
        return this == NORMAL || this == RED_CHECK || this == BLACK_CHECK;
    }
}