package progetto.mp.vannacci.serena.library.service.exporting;

import java.util.Iterator;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibraryComponent;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.exporting.format.indentation.IndentationFormatter;
import progetto.mp.vannacci.serena.library.service.exporting.format.syntax.FieldNames;

public final class XmlExportVisitor extends ExportVisitorTemplate {

	public XmlExportVisitor(IndentationFormatter formatter) {
		super(formatter);
	}

	@Override
	protected String formatHeader(String componentType) {
		return indentXmlTag(XmlSyntax.OPEN_PREFIX, componentType);
	}

	@Override
	protected String formatFooter(String componentType) {
		return indentXmlTag(XmlSyntax.CLOSE_PREFIX, componentType);
	}

	@Override
	protected String formatEBookBody(EBook ebook) {
		return indentBlock(() -> formatIndentedEBookBlock(ebook));
	}

	private String formatIndentedEBookBlock(EBook ebook) {
		return formatIndentedXmlElement(FieldNames.EBOOK_TITLE, ebook.getLibraryComponentName())
				+ formatIndentedXmlElement(FieldNames.EBOOK_AUTHOR, ebook.getAuthor())
				+ formatIndentedXmlElement(FieldNames.EBOOK_SIZE_MB, String.valueOf(ebook.calculateSizeInMB()));
	}

	@Override
	protected String formatLibrarySectionBody(LibrarySection section) {
		return indentBlock(() -> formatIndentedLibrarySectionBlock(section));
	}

	private String formatIndentedLibrarySectionBlock(LibrarySection section) {
		return formatIndentedXmlElement(FieldNames.SECTION_NAME, section.getLibraryComponentName())
				+ formatIndentedLibraryComponentsBlock(section);
	}

	private String formatIndentedLibraryComponentsBlock(LibrarySection section) {
		return indentXmlTag(XmlSyntax.OPEN_PREFIX, FieldNames.LIBRARY_COMPONENTS)
				+ iterSectionComponents(section)
				+ indentXmlTag(XmlSyntax.CLOSE_PREFIX, FieldNames.LIBRARY_COMPONENTS);
	}

	private String formatIndentedXmlElement(String tag, String content) {
		return indentLine(
				formatXmlTag(XmlSyntax.OPEN_PREFIX, tag)
				+ content
				+ formatXmlTag(XmlSyntax.CLOSE_PREFIX, tag));
	}

	private String indentXmlTag(String xmlPrefix, String tag) {
		return indentLine(formatXmlTag(xmlPrefix, tag));
	}

	private String formatXmlTag(String xmlPrefix, String tag) {
		return xmlPrefix
				+ tag
				+ XmlSyntax.SUFFIX;
	}

	private String iterSectionComponents(LibrarySection section) {
		Iterator<LibraryComponent> iterator = section.iterator();
		if (!iterator.hasNext())
			return "";
		return buildIndentedComponentsBlock(iterator);
	}

	private String buildIndentedComponentsBlock(Iterator<LibraryComponent> iterator) {
		StringBuilder builder = new StringBuilder();
		builder.append(indentComponent(iterator.next()));

		while (iterator.hasNext()) {
			builder.append(newLine())
					.append(indentComponent(iterator.next()));
		}

		return builder.append(newLine()).toString();
	}

	private String indentComponent(LibraryComponent component) {
		return indentBlock(() -> component.accept(this));
	}
	
	private static final class XmlSyntax {

		private XmlSyntax() {
		}

		public static final String OPEN_PREFIX = "<";
		public static final String CLOSE_PREFIX = "</";
		public static final String SUFFIX = ">";
	}
}