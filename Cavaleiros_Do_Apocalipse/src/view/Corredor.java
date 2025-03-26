package view;

import controller.ThreadCavaleiro;

public class Corredor {
	public static void main(String[] args) {
		String[] cavaleiros = { "Conquista", "Guerra", "Fome", "Morte" };

		for (String cavaleiro : cavaleiros) {
			boolean isPedra = false;
			boolean isTocha = false;
			new ThreadCavaleiro(cavaleiro, isTocha, isPedra).start();
		}

	}

}
