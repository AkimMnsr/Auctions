package fr.eni.javaee.auctions.messages;

import java.util.ResourceBundle;

public class LecteurMessagesEnchere {

	private static ResourceBundle rb;

	static {

		try {
			rb = ResourceBundle.getBundle("fr.eni.javaee.auctions.messages.messages_erreur_enchere");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMessageErreur(int code) {
		String message = "";
		try {
			if (rb != null) {
				message = rb.getString(String.valueOf(code));
			} else {
				message = "Un problème est survenue lors de la lecture du fichier 'messages_erreur_enchere.properties'";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Erreur inconnue";
		}

		return message;
	}
}
