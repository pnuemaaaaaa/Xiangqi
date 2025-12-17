package Initialiazation;

public enum Player {
    RED("红方"),
    BLACK("黑方");

    private final String displayName;

    private Player(String displayName) {
        this.displayName = displayName;

    }

    public String getDisplayName() {
        return displayName;
    }
}
