package progetto.mp.vannacci.serena.library.service.sharing.adapter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibrarySection;

public class MailSenderAdapterTest {

	private SharingService adapter;

	@Before
	public void setUp() {
		adapter = new MailSenderAdapter("test@domain.com");
	}

	@Test
	public void shouldSendExpectedEmailForEBookTest() {
		assertEquals(
				"Email sent from: test@domain.com\n"
				+ "Subject: Ebook\n"
				+ "Content: JUnit",
				adapter.send(new EBook("JUnit", "Author Name", 2), "JUnit"));
	}

	@Test
	public void shouldSendExpectedEmailForLibrarySectionTest() {
		assertEquals(
				"Email sent from: test@domain.com\n"
				+ "Subject: Library Section\n"
				+ "Content: Science",
				adapter.send(new LibrarySection("Science", List.of()), "Science"));
	}

}
