package progetto.mp.vannacci.serena.library.service.sharing.adapter;

public class MailSender {

	private final String senderEmailAddress;

	public MailSender(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	public String sendEmail(String subject, String content) {
		return new StringBuilder()
				.append("Email sent from: ").append(senderEmailAddress)
				.append("\n")
				.append("Subject: ").append(subject)
				.append("\n")
				.append("Content: ").append(content)
				.toString();
	}
}
