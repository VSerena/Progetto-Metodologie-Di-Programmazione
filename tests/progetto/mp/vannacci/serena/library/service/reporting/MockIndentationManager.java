package progetto.mp.vannacci.serena.library.service.reporting;

import java.util.function.Supplier;

import progetto.mp.vannacci.serena.library.service.exporting.format.indentation.IndentationFormatter;

public final class MockIndentationManager implements IndentationFormatter {
	private final String indentationUnit;
	private int indentLevel = 0;

	public MockIndentationManager(String unitSymbol, int width) {
		this.indentationUnit = unitSymbol.repeat(width);
	}

	@Override
	public String indent(String line) {
		return indentationUnit.repeat(indentLevel) + line;
	}

	@Override
	public String newLine() {
		return "\n";
	}

	@Override
	public String indentBlock(Supplier<String> block) {
		indentLevel++;
		try {
			return block.get();
		} finally {
			indentLevel--;
		}
	}

}
