package view;

import java.util.Random;

import controller.Aeronave;

public class Aeroporto {
	   public static void main(String[] args) {
	        String[] pistas = {"Norte", "Sul"};
	        Random random = new Random();
	        
	        for (int i = 1; i <= 12; i++) {
	            String pistaEscolhida = pistas[random.nextInt(2)];
	            new Aeronave("Aeronave " + i, pistaEscolhida).start();
	        }
	    }

}
