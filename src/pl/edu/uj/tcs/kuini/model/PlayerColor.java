package pl.edu.uj.tcs.kuini.model;

public enum PlayerColor {
    RED(255, 0, 0), BLUE(0, 0, 255), GOLD(255, 215, 0), GREEN(0, 255, 0), BLACK(0,0,0);
    private final int R, G, B;

    PlayerColor(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }
    
}
