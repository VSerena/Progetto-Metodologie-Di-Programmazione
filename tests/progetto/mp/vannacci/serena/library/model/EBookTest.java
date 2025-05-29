package progetto.mp.vannacci.serena.library.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import progetto.mp.vannacci.serena.library.service.sharing.adapter.MailSenderAdapter;

public class EBookTest {

	private EBook ebook;

	@Before
	public void setup() {
		ebook = new EBook("Clean Code", "Robert Martin", 5);
	}

	@Test
	public void getEBookDetailsTest() {
		assertEquals(
				"Title: Clean Code,"
				+ " Author: Robert Martin,"
				+ " Size (MB): 5",
				ebook.getEBookDetails());
	}

	@Test
	public void shouldThrowIfTitleIsNullTest() {
		assertEquals("Name must not be null or blank.", 
				assertThrows(IllegalArgumentException.class,
						() -> new EBook(null, "Robert Martin", 5))
				.getMessage());
	}

	@Test
	public void shouldThrowIfTitleIsBlankTest() {
		assertEquals("Name must not be null or blank.", 
				assertThrows(IllegalArgumentException.class,
				() -> new EBook(" ", "Robert Martin", 5))
				.getMessage());
	}

	@Test
	public void shouldThrowIfAuthorIsNullTest() {
		assertEquals("Author must not be null or blank.", 
				assertThrows(IllegalArgumentException.class,
						() -> new EBook("Clean Code", null, 5))
				.getMessage());
	}

	@Test
	public void shouldThrowIfAuthorIsBlankTest() {
		assertEquals("Author must not be null or blank.", 
				assertThrows(IllegalArgumentException.class,
						() -> new EBook("Clean Code", " ", 5))
				.getMessage());
	}

	@Test
	public void shouldThrowIfSizeInMBIsNegativeTest() {
		assertEquals("Size must be positive (received -15)", 
				assertThrows(IllegalArgumentException.class,
						() -> new EBook("Clean Code", "Robert Martin", -15))
				.getMessage());
	}
	
	@Test
	public void shouldThrowIfSizeInMBIsZeroTest() {
		assertEquals("Size must be positive (received 0)", 
				assertThrows(IllegalArgumentException.class,
						() -> new EBook("Clean Code", "Robert Martin", 0))
				.getMessage());
	}


	@Test
	public void shouldCalculateSizeInMBTest() {
		assertEquals(5, ebook.calculateSizeInMB(), 0);
	}

	@Test
	public void shouldShareExpectedEmailContentTest() {
		assertEquals(
				"Email sent from: test@domain.com\n"
				+ "Subject: Ebook\n"
				+ "Content: Clean Code",
				ebook.share(new MailSenderAdapter("test@domain.com")));
	}
}
