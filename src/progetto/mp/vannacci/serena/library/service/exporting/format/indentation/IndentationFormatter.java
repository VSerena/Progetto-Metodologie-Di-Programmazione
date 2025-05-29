package progetto.mp.vannacci.serena.library.service.exporting.format.indentation;

import java.util.function.Supplier;

public interface IndentationFormatter {

	String indent(String line);

	String newLine();

	String indentBlock(Supplier<String> block);
}
