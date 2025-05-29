package progetto.mp.vannacci.serena.library.service.exporting;

import java.util.function.Supplier;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.LibraryVisitor;
import progetto.mp.vannacci.serena.library.service.exporting.format.indentation.IndentationFormatter;
import progetto.mp.vannacci.serena.library.service.exporting.format.syntax.FieldNames;

public abstract class ExportVisitorTemplate implements LibraryVisitor<String> {

	private final IndentationFormatter formatter;

	protected ExportVisitorTemplate(IndentationFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public final String visitEBook(EBook ebook) {
		return formatHeader(FieldNames.COMPONENT_EBOOK)
				+ formatEBookBody(ebook)
				+ formatFooter(FieldNames.COMPONENT_EBOOK);
	}

	@Override
	public final String visitLibrarySection(LibrarySection section) {
		return formatHeader(FieldNames.COMPONENT_SECTION)
				+ formatLibrarySectionBody(section)
				+ formatFooter(FieldNames.COMPONENT_SECTION);
	}

	protected abstract String formatHeader(String componentType);

	protected abstract String formatFooter(String componentType);

	protected abstract String formatEBookBody(EBook ebook);

	protected abstract String formatLibrarySectionBody(LibrarySection section);

	private String indent(String line) {
		return formatter.indent(line);
	}

	protected String newLine() {
		return formatter.newLine();
	}

	protected String indentBlock(Supplier<String> block) {
		return formatter.indentBlock(block);
	}

	protected String indentLine(String line) {
		return indent(line) + newLine();
	}

}