package progetto.mp.vannacci.serena.library.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progetto.mp.vannacci.serena.library.service.sharing.adapter.MailSenderAdapter;

public class LibrarySectionTest {

	private LibrarySection section;
	private EBook ebook1;
	private EBook ebook2;
	private LibrarySection nestedSection1;
	private LibrarySection nestedSection2;

	@Before
	public void setup() {

		section = new LibrarySection("Programming", new ArrayList<>());
		nestedSection1 = new LibrarySection("Clean Code", new ArrayList<>());
		nestedSection2 = new LibrarySection("Design Patterns", new ArrayList<>());
		ebook1 = new EBook("Clean Code", "Robert Martin", 5);
		ebook2 = new EBook("Design Patterns", "Gamma et al.", 4);
	}

	@Test
	public void shouldThrowIfNameIsNullTest() {
		assertEquals("Name must not be null or blank.",
				assertThrows(IllegalArgumentException.class,
						() -> new LibrarySection(null, new ArrayList<>()))
				.getMessage());

	}

	@Test
	public void shouldThrowIfNameIsBlankTest() {
		assertEquals("Name must not be null or blank.", 
				assertThrows(IllegalArgumentException.class,
						() -> new LibrarySection(" ", new ArrayList<>()))
				.getMessage());
	}

	@Test
	public void shouldAddComponentTest() {
		section.addComponent(ebook1);
		section.addComponent(ebook2);

		assertEquals(2, section.getComponents().size());
		assertTrue(section.getComponents().contains(ebook1));
		assertTrue(section.getComponents().contains(ebook2));
	}

	@Test
	public void shouldRemoveComponentTest() {
		EBook ebookToRemove = new EBook("Clean Code", "Robert Martin", 5);
		EBook ebookToNotRemove = new EBook("Design Patterns", "Gamma et al.", 4);
		section.getComponents().addAll(Arrays.asList(ebookToNotRemove, ebookToRemove));
		section.removeComponent(ebookToRemove);

		assertEquals(1, section.getComponents().size());
		assertFalse(section.getComponents().contains(ebookToRemove));
		assertTrue(section.getComponents().contains(ebookToNotRemove));
	}

	@Test
	public void shouldNotIterateNestedSectionsTest() {
		section.getComponents().add(ebook1);
		section.getComponents().add(nestedSection1);
		nestedSection1.getComponents().add(ebook2);
		List<LibraryComponent> iterated = new ArrayList<>();
		section.iterator().forEachRemaining(iterated::add);

		assertTrue(iterated.contains(ebook1));
		assertTrue(iterated.contains(nestedSection1));
		assertFalse(iterated.contains(ebook2));
		assertEquals(2, iterated.size());
	}

	@Test
	public void shouldFindByComponentNameTest() {
		section.getComponents().addAll(Arrays.asList(ebook1, ebook2));

		assertTrue(section.findComponentByName("Design Patterns").isPresent());
		assertEquals(ebook2, section.findComponentByName("Design Patterns").get());
		assertFalse(section.findComponentByName("Not Existing ebook").isPresent());
	}

	@Test
	public void shouldCalculateSizeInMBTest() {
		section.getComponents().addAll(Arrays.asList(ebook1, ebook2));

		assertEquals(9, section.calculateSizeInMB());
	}

	@Test
	public void shouldCalculateSizeInMBWithNestedSectionsTest() {
		nestedSection1.getComponents().addAll(Arrays.asList(ebook1, ebook2));
		section.getComponents().addAll(Arrays.asList(
				new EBook("Java", "Walter Savitch", 9), nestedSection1));

		assertEquals(18, section.calculateSizeInMB());
	}

	@Test
	public void shouldCalculateSizeInMBWithMultipleNestedSectionsTest() {
		nestedSection1.getComponents().addAll(Arrays.asList(ebook1));
		nestedSection2.getComponents().addAll(Arrays.asList(ebook2));
		section.getComponents().addAll(Arrays.asList(
				new EBook("Java", "Walter Savitch", 9), nestedSection1, nestedSection2));

		assertEquals(18, section.calculateSizeInMB());
	}

	@Test
	public void shouldShareExpectedEmailContentForLibrarySectionWithEBooksTest() {
		section.getComponents().addAll(Arrays.asList(ebook1, ebook2));

		assertEquals(
				"Email sent from: test@domain.com\n"
				+ "Subject: Library Section\n"
				+ "Content: Programming\n"
				+ " - Clean Code\n"
				+ " - Design Patterns",
				section.share(new MailSenderAdapter("test@domain.com")));
	}
}
