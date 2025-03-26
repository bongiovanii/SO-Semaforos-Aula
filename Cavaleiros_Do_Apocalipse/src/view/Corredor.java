package view;

import controller.ThreadCavaleiro;

public class Corredor {
    public static void main(String[] args) {
        String[] cavaleiros = { "Conquista", "Guerra", "Fome", "Morte" };

        for (String cavaleiro : cavaleiros) {
            new ThreadCavaleiro(cavaleiro).start();
        }
    }
}
