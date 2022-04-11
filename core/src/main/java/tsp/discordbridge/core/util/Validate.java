package tsp.discordbridge.core.util;

/**
 * Validation checks
 */
public class Validate {

    public static <T> void notNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " can not be null!");
        }
    }

    public static <T> void validate(T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " is null!");
        }
    }

}
