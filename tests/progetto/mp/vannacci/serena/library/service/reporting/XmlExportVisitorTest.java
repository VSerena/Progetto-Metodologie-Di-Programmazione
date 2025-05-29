package progetto.mp.vannacci.serena.library.service.reporting;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.exporting.XmlExportVisitor;

public class XmlExportVisitorTest {

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
	public void shouldExportEBookAsXmlFormatTest() {
		assertEquals(
				"<EBook>\n"
				+ "  <title>Clean Code</title>\n"
				+ "  <author>Martin</author>\n"
				+ "  <sizeInMB>5</sizeInMB>\n"
				+ "</EBook>\n",
				cleanCode.accept(new XmlExportVisitor(indentationManager)));
	}

	@Test
	public void shouldExportNestedSectionsAsXmlFormatTest() {
		javaSection.addComponent(effectiveJava);
		javaSection.addComponent(javaConcurrency);
		programmingSection.addComponent(cleanCode);
		programmingSection.addComponent(javaSection);

		assertEquals(
				"<LibrarySection>\n"
				+ "  <name>Programming</name>\n"
				+ "  <components>\n"
				+ "    <EBook>\n"
				+ "      <title>Clean Code</title>\n"
				+ "      <author>Martin</author>\n"
				+ "      <sizeInMB>5</sizeInMB>\n"
				+ "    </EBook>\n"
				+ "\n"
				+ "    <LibrarySection>\n"
				+ "      <name>Java</name>\n"
				+ "      <components>\n"
				+ "        <EBook>\n"
				+ "          <title>Effective Java</title>\n"
				+ "          <author>Bloch</author>\n"
				+ "          <sizeInMB>3</sizeInMB>\n"
				+ "        </EBook>\n"
				+ "\n"
				+ "        <EBook>\n"
				+ "          <title>Java Concurrency</title>\n"
				+ "          <author>Goetz</author>\n"
				+ "          <sizeInMB>4</sizeInMB>\n"
				+ "        </EBook>\n"
				+ "\n"
				+ "      </components>\n"
				+ "    </LibrarySection>\n"
				+ "\n"
				+ "  </components>\n"
				+ "</LibrarySection>\n"
				,
				programmingSection.accept(new XmlExportVisitor(indentationManager)));
	}
}
