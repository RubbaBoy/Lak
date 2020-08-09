package com.uddernetworks.lak.pi.gpio;

/**
 * A low-level class to handle pins based on the /sys/class/gpio path.
 * See <a href=https://www.kernel.org/doc/Documentation/gpio/sysfs.txt>https://www.kernel.org/doc/Documentation/gpio/sysfs.txt</a>
 * for official documentation, which this class matches up with.
 */
public interface PinHandler {

    void exportPin(int pin);

    void unexportPin(int pin);

    void setDirection(int pin, PinDirection pinDirection);

    void setValue(int pin, PinValue value);

    PinValue readValue(int pin);

    void setEdge(int pin, PinEdge pinEdge);

    void setActiveLow(int pin, PinActiveLow pinActiveLow);
}

enum PinDirection {
    IN("in"),
    OUT("out");

    private final String direction;

    PinDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}

enum PinValue {
    LOW(0),
    HIGH(1);

    private final int value;

    PinValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PinValue fromValue(int value) {
        return value == 0 ? LOW : HIGH;
    }

    public boolean isHigh() {
        return this == HIGH;
    }
}

enum PinEdge {
    NONE("none"),
    RISING("rising"),
    FALLING("falling"),
    BOTH("both");

    private final String edge;

    PinEdge(String edge) {
        this.edge = edge;
    }

    public String getEdge() {
        return edge;
    }
}

enum PinActiveLow {
    FALSE(0),
    TRUE(1);

    private final int value;

    PinActiveLow(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
