package de.intranda.digiverso.model.icinga;

public enum Icinga2HostStatus {
    UP,
    DOWN;

    public static Icinga2HostStatus fromCode(int code) {
        return switch (code) {
            case 0, 1 -> UP;
            case 2, 3 -> DOWN;
            default -> throw new IllegalStateException("Unexpected code: " + code);
        };
    }
}
