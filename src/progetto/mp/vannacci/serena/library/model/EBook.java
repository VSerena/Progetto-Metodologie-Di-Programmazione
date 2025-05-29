package progetto.mp.vannacci.serena.library.model;

import progetto.mp.vannacci.serena.library.common.ValidationUtils;
import progetto.mp.vannacci.serena.library.service.LibraryVisitor;

public final class EBook extends LibraryComponent {

	private final String author;
	private final int sizeInMB;

	public EBook(String title, String author, int sizeInMB) {
		super(title);
		validateEBook(author, sizeInMB);
		this.author = author;
		this.sizeInMB = sizeInMB;
	}

	private static void validateEBook(String author, int sizeInMB) {
		ValidationUtils.validateNotBlankOrNull(author, "Author must not be null or blank.");
		ValidationUtils.validatePositive(sizeInMB, "Size must be positive");
	}

	@Override
	public <T> T accept(LibraryVisitor<T> visitor) {
		return visitor.visitEBook(this);
	}

	@Override
	public int calculateSizeInMB() {
		return sizeInMB;
	}

	@Override
	protected String buildShareContent() {
		return getLibraryComponentName();
	}

	public String getAuthor() {
		return author;
	}

	String getEBookDetails() {
		return "Title: " + getLibraryComponentName()
				+ ", Author: " + getAuthor()
				+ ", Size (MB): " + calculateSizeInMB();
	}

}