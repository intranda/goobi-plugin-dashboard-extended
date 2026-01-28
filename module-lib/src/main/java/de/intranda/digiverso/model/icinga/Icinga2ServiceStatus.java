package de.intranda.digiverso.model.icinga;

public enum Icinga2ServiceStatus {
    OK,
    WARNING,
    CRITICAL,
    UNKNOWN;

    public static Icinga2ServiceStatus fromCode(int code) {
        return switch (code) {
            case 0 -> OK;
            case 1 -> WARNING;
            case 2 -> CRITICAL;
            case 3 -> UNKNOWN;
            default -> throw new IllegalStateException("Unexpected code: " + code);
        };
    }
}
