package progetto.mp.vannacci.serena.library.common;

public final class ValidationUtils {

	private ValidationUtils() {
	}

	public static void validateNotBlankOrNull(String value, String message) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void validatePositive(Number value, String message) {
		if (value.doubleValue() <= 0) {
			throw new IllegalArgumentException(message + " (received " + value + ")");
		}
	}

}