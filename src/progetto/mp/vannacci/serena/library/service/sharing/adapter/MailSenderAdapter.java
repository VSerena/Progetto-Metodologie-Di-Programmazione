package progetto.mp.vannacci.serena.library.service.sharing.adapter;

import progetto.mp.vannacci.serena.library.model.EBook;
import progetto.mp.vannacci.serena.library.model.LibraryComponent;
import progetto.mp.vannacci.serena.library.model.LibrarySection;
import progetto.mp.vannacci.serena.library.service.LibraryVisitor;

public class MailSenderAdapter extends MailSender implements SharingService {

	public MailSenderAdapter(String senderEmail) {
		super(senderEmail);
	}

	@Override
	public String send(LibraryComponent component, String content) {
		return sendEmail(generateEmailSubject(component), content);
	}

	private String generateEmailSubject(LibraryComponent component) {
		return component.accept(new LibraryVisitor<>() {
			@Override
			public String visitEBook(EBook ebook) {
				return "Ebook";
			}

			@Override
			public String visitLibrarySection(LibrarySection section) {
				return "Library Section";
			}
		});
	}
}
