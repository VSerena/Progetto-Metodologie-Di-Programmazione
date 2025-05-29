package progetto.mp.vannacci.serena.library.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ValidationUtilsTest {

	@Test
	public void shouldAcceptValidStringTest() {
		ValidationUtils.validateNotBlankOrNull("Valid String", "String must not be blank or null.");
	}

	@Test
	public void shouldThrowIfStringValueIsBlankTest() {
		assertEquals("String must not be blank or null.",
				assertThrows(IllegalArgumentException.class,
						() -> ValidationUtils.validateNotBlankOrNull("  ", "String must not be blank or null."))
				.getMessage());
	}

	@Test
	public void shouldThrowIfStringValueIsNullTest() {
		assertEquals("String must not be blank or null.",
				assertThrows(IllegalArgumentException.class,
						() -> ValidationUtils.validateNotBlankOrNull(null, "String must not be blank or null."))
				.getMessage());
	}

	@Test
	public void shouldThrowIfStringValueIsEmptyTest() {
		assertEquals("String must not be blank or null.",
				assertThrows(IllegalArgumentException.class,
						() -> ValidationUtils.validateNotBlankOrNull("", "String must not be blank or null."))
				.getMessage());
	}

	@Test
	public void shouldAcceptPositiveValueTest() {
		ValidationUtils.validatePositive(10, "Negative not allowed");
	}

	@Test
	public void shouldThrowIfValueIsZeroTest() {
		assertEquals("Negative not allowed (received 0)", 
				assertThrows(IllegalArgumentException.class,
						() -> ValidationUtils.validatePositive(0, "Negative not allowed"))
				.getMessage());
	}

	@Test
	public void shouldThrowIfValueIsNegativeTest() {
		assertEquals("Negative not allowed (received -10)", 
				assertThrows(IllegalArgumentException.class,
						() -> ValidationUtils.validatePositive(-10, "Negative not allowed"))
				.getMessage());
	}

}
