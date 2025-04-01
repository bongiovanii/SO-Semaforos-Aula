package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Aeronave extends Thread {
	private static final Random random = new Random();
    private static final Semaphore semaforoDecolagem = new Semaphore(2);
    private String nome;
    private String pista;

    public Aeronave(String nome, String pista) {
        this.nome = nome;
        this.pista = pista;
    }

    private void esperar(int min, int max) throws InterruptedException {
        Thread.sleep(random.nextInt(max - min + 1) + min);
    }

    @Override
    public void run() {
        try {
            System.out.println(nome + " iniciando manobra na " + pista);
            esperar(300, 700);

            System.out.println(nome + " taxiando na " + pista);
            esperar(500, 1000);

            semaforoDecolagem.acquire();
            System.out.println(nome + " iniciando decolagem na " + pista);
            esperar(600, 800);
            System.out.println(nome + " afastando-se da " + pista);
            esperar(300, 800);
            semaforoDecolagem.release();

            System.out.println(nome + " decolagem concluída na " + pista);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
