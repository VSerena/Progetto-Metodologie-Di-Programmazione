package progetto.mp.vannacci.serena.library.service.reporting;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.exporting.JsonExportVisitor;

public class JsonExportVisitorTest {

	private LibrarySection programmingSection;
	private EBook cleanCode;
	private LibrarySection javaSection;
	private EBook effectiveJava;
	private EBook javaConcurrency;
	private MockIndentationManager indentationManager;

	@Before
	public void setup() {
		programmingSection = new LibrarySection("Programming", new ArrayList<>());
		cleanCode = new EBook("Clean Code", "Martin", 5);
		javaSection = new LibrarySection("Java", new ArrayList<>());
		effectiveJava = new EBook("Effective Java", "Bloch", 3);
		javaConcurrency = new EBook("Java Concurrency", "Goetz", 4);
		indentationManager = new MockIndentationManager(" ", 2);
	}

	@Test
	public void shouldExportEBookAsJsonFormatTest() {
		assertEquals(
				"{\n"
				+ "  \"type\": \"EBook\",\n"
				+ "  \"title\": \"Clean Code\",\n"
				+ "  \"author\": \"Martin\",\n"
				+ "  \"sizeInMB\": 5\n"
				+ "}\n",
				cleanCode.accept(new JsonExportVisitor(indentationManager)));
	}

	@Test
	public void shouldExportNestedSectionsAsJsonFormatTest() {
		javaSection.addComponent(effectiveJava);
		javaSection.addComponent(javaConcurrency);
		programmingSection.addComponent(cleanCode);
		programmingSection.addComponent(javaSection);

		assertEquals(
				 "{\n"
				 + "  \"type\": \"LibrarySection\",\n"
				 + "  \"name\": \"Programming\",\n"
				 + "  \"components\": [\n"
				 + "      {\n"
				 + "        \"type\": \"EBook\",\n"
				 + "        \"title\": \"Clean Code\",\n"
				 + "        \"author\": \"Martin\",\n"
				 + "        \"sizeInMB\": 5\n"
				 + "      }\n"
				 + ",\n"
				 + "      {\n"
				 + "        \"type\": \"LibrarySection\",\n"
				 + "        \"name\": \"Java\",\n"
				 + "        \"components\": [\n"
				 + "            {\n"
				 + "              \"type\": \"EBook\",\n"
				 + "              \"title\": \"Effective Java\",\n"
				 + "              \"author\": \"Bloch\",\n"
				 + "              \"sizeInMB\": 3\n"
				 + "            }\n"
				 + ",\n"
				 + "            {\n"
				 + "              \"type\": \"EBook\",\n"
				 + "              \"title\": \"Java Concurrency\",\n"
				 + "              \"author\": \"Goetz\",\n"
				 + "              \"sizeInMB\": 4\n"
				 + "            }\n"
				 + "\n"
				 + "        ]\n"
				 + "      }\n"
				 + "\n"
				 + "  ]\n"
				 + "}\n",
				programmingSection.accept(new JsonExportVisitor(indentationManager)));
	}

}
