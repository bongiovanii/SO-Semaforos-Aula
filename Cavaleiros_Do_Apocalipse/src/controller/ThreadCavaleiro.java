package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {
    private static final Semaphore semaforo = new Semaphore(4);
    private static final Semaphore semaforoTocha = new Semaphore(1);
    private static final Semaphore semaforoPedra = new Semaphore(1);
    private static final Semaphore semaforoPorta1 = new Semaphore(1);
    private static final Semaphore semaforoPorta2 = new Semaphore(1);
    private static final Semaphore semaforoPorta3 = new Semaphore(1);
    private static final Semaphore semaforoPorta4 = new Semaphore(1);

    private final String cavaleiro;
    private static boolean tochaPegada = false;
    private static boolean pedraPegada = false;

    public ThreadCavaleiro(String cavaleiro) {
        this.cavaleiro = cavaleiro;
    }

    @Override
    public void run() {
        try {
            semaforo.acquire();
            cavaleiroAndou();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    private void cavaleiroAndou() {
        Random rand = new Random();
        int min = 2, max = 4;
        int deslocamento = rand.nextInt((max - min) + 1) + min;
        int distanciaCorredor = 2000;
        int distanciaPercorrida = 0;

        while (distanciaPercorrida < distanciaCorredor) {
            distanciaPercorrida += deslocamento;
            System.out.println("O cavaleiro " + cavaleiro + " andou " + distanciaPercorrida + "m.");

            if (distanciaPercorrida >= 500 && !tochaPegada) {
                pegarTocha();
                deslocamento += 2;
            }

            if (distanciaPercorrida >= 1500 && !pedraPegada) {
                pegarPedra();
                deslocamento += 2;
            }

            if (distanciaPercorrida >= 2000) {
                escolherPorta();
            }
        }
    }

    private void escolherPorta() {
        Random rand = new Random();
        boolean entrou = false;

        while (!entrou) {
            int escolha = rand.nextInt(4) + 1;

            switch (escolha) {
                case 1:
                    entrou = tentarEntrarNaPorta(semaforoPorta1, "Porta 1");
                    break;
                case 2:
                    entrou = tentarEntrarNaPorta(semaforoPorta2, "Porta 2");
                    break;
                case 3:
                    entrou = tentarEntrarNaPorta(semaforoPorta3, "Porta 3");
                    break;
                case 4:
                    entrou = tentarEntrarNaPorta(semaforoPorta4, "Porta 4");
                    break;
            }
        }
    }

    private boolean tentarEntrarNaPorta(Semaphore semaforoPorta, String nomePorta) {
        if (semaforoPorta.tryAcquire()) {
            System.out.println("🚪 O cavaleiro " + cavaleiro + " entrou na " + nomePorta);
            if (nomePorta.equals("Porta 3")) {
                System.out.println("✅ O cavaleiro " + cavaleiro + " vai sobreviver mais um dia!");
            } else {
                System.out.println("❌ A não! O cavaleiro " + cavaleiro + " escolheu a porta errada, foi comido por um monstro e morreu!");
            }
            return true;
        } else {
            return false; 
        }
    }

    private void pegarTocha() {
        if (semaforoTocha.tryAcquire()) {
            tochaPegada = true;
            System.out.println("🏮 O cavaleiro " + cavaleiro + " pegou a tocha!");
        } else {
            System.out.println("❌ " + cavaleiro + ": alguém já pegou a tocha...");
        }
    }

    private void pegarPedra() {
        if (semaforoPedra.tryAcquire()) {
            pedraPegada = true;
            System.out.println("🪨 O cavaleiro " + cavaleiro + " pegou a pedra!");
        } else {
            System.out.println("❌ " + cavaleiro + ": alguém já pegou a pedra...");
        }
    }
}
