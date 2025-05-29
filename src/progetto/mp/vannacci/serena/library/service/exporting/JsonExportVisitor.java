package progetto.mp.vannacci.serena.library.service.exporting;

import java.util.Iterator;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibraryComponent;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.exporting.format.indentation.IndentationFormatter;
import progetto.mp.vannacci.serena.library.service.exporting.format.syntax.FieldNames;

public final class JsonExportVisitor extends ExportVisitorTemplate {

	public JsonExportVisitor(IndentationFormatter formatter) {
		super(formatter);
	}

	@Override
	protected String formatHeader(String componentType) {
		return indentLine(JsonSyntax.OPEN_BRACE)
				+ indentBlock(() -> formatKeyValue(FieldNames.COMPONENT_TYPE, componentType));
	}

	@Override
	protected String formatFooter(String componentType) {
		return indentLine(JsonSyntax.CLOSE_BRACE);
	}

	@Override
	protected String formatEBookBody(EBook ebook) {
		return indentBlock(() -> formatEBookBlock(ebook));
	}

	private String formatEBookBlock(EBook ebook) {
		return formatKeyValue(FieldNames.EBOOK_TITLE, ebook.getLibraryComponentName())
				+ formatKeyValue(FieldNames.EBOOK_AUTHOR, ebook.getAuthor())
				+ formatKeyValue(FieldNames.EBOOK_SIZE_MB, ebook.calculateSizeInMB());
	}

	@Override
	protected String formatLibrarySectionBody(LibrarySection section) {
		return indentBlock(
				() -> formatKeyValue(FieldNames.SECTION_NAME, section.getLibraryComponentName())
				+ formatComponentsArrayBlock(section));
	}

	private String formatKeyValue(String key, String value) {
		return indentLine(
				quote(key)
				+ JsonSyntax.KEY_VALUE_SEPARATOR
				+ quote(value)
				+ JsonSyntax.COMMA);
	}

	private String formatKeyValue(String key, int value) {
		return indentLine(
				quote(key)
				+ JsonSyntax.KEY_VALUE_SEPARATOR
				+ value);
	}

	private String quote(String content) {
		return JsonSyntax.QUOTE
				+ content
				+ JsonSyntax.QUOTE;
	}

	private String formatComponentsArrayBlock(LibrarySection section) {
		return indentLine(
				quote(FieldNames.LIBRARY_COMPONENTS)
				+ JsonSyntax.KEY_VALUE_SEPARATOR
				+ JsonSyntax.ARRAY_OPEN)
				+ indentBlock(() -> formatComponentArrayBody(section))
				+ newLine() 
				+ indentLine(JsonSyntax.ARRAY_CLOSE);
	}

	private String formatComponentArrayBody(LibrarySection section) {
		Iterator<LibraryComponent> iterator = section.iterator();
		StringBuilder builder = new StringBuilder();

		while (iterator.hasNext()) {
			builder.append(indentComponent(iterator.next()));
			if (iterator.hasNext()) {
				builder.append(JsonSyntax.COMMA)
						.append(newLine());
			}
		}

		return builder.toString();
	}

	private String indentComponent(LibraryComponent component) {
		return indentBlock(() -> component.accept(this));
	}
	
	private static final class JsonSyntax {

		private JsonSyntax() {
		}

		public static final String OPEN_BRACE = "{";
		public static final String CLOSE_BRACE = "}";
		public static final String ARRAY_OPEN = "[";
		public static final String ARRAY_CLOSE = "]";
		public static final String KEY_VALUE_SEPARATOR = ": ";
		public static final String COMMA = ",";
		public static final String QUOTE = "\"";
	}
}
