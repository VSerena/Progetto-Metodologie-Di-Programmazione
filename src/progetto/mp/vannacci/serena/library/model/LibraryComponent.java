package progetto.mp.vannacci.serena.library.model;

import progetto.mp.vannacci.serena.library.common.ValidationUtils;
import progetto.mp.vannacci.serena.library.service.LibraryVisitor;
import progetto.mp.vannacci.serena.library.service.sharing.adapter.SharingService;

public abstract class LibraryComponent {

	private final String libraryComponentName;

	protected LibraryComponent(String libraryComponentName) {
		validateComponentName(libraryComponentName);
		this.libraryComponentName = libraryComponentName;
	}

	private static void validateComponentName(String libraryComponentName) {
		ValidationUtils.validateNotBlankOrNull(libraryComponentName, "Name must not be null or blank.");
	}

	public final String share(SharingService sender) {
		return sender.send(this, buildShareContent());
	};

	protected abstract String buildShareContent();

	public abstract int calculateSizeInMB();

	public abstract <T> T accept(LibraryVisitor<T> visitor);

	public String getLibraryComponentName() {
		return libraryComponentName;
	}

}