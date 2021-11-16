package Enums;

public enum Size {
    ONE(1),
    FIVE(5),
    TEN(10);


    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
