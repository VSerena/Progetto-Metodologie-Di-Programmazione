package progetto.mp.vannacci.serena.library.service;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibrarySection;

public interface LibraryVisitor<T> {

	T visitEBook(EBook ebook);

	T visitLibrarySection(LibrarySection section);
}
