package tn.solixy.delivite.entities;

public enum Rating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);
    public final int value;
    Rating(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
