package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread{
	private static final Semaphore semaforo = new Semaphore(1);
	private static final Semaphore semaforoTocha = new Semaphore(1);
	private static final Semaphore semaforoPedra= new Semaphore(1);

	private final String cavaleiro;
	
	private boolean isTocha = false;
	private boolean isPedra = false;
	
	public ThreadCavaleiro(String cavaleiro, boolean isTocha, boolean isPedra) {
		this.cavaleiro = cavaleiro;
		this.isTocha = isTocha;
		this.isPedra = isPedra;
	}
	
	public void run() {
		try {
			semaforo.acquire();		
			cavaleiroAndou();
			Thread.sleep(50);
			
		}catch(InterruptedException e) {
			e.fillInStackTrace();
		}
			
	}	
	


	private void cavaleiroAndou() {
		Random rand = new Random();
		int min = 2;
		int max = 4;
		int deslocamento = rand.nextInt((max - min) + 1) + min;;
		
		int distanciaCorredor = 2000;
		int distanciaPercorrida = 0;
		
		while(distanciaPercorrida < distanciaCorredor) {
			distanciaPercorrida += deslocamento;
			System.out.println("O cavaleiro "+ cavaleiro + " andou " + distanciaPercorrida + "m.");
			if(distanciaPercorrida == 500) {
				try {
					semaforoTocha.acquire();
					System.out.println("O cavaleiro "+ cavaleiro + " pegou a tocha");
					isTocha = true;
					
				}catch (Exception e) {
					e.fillInStackTrace();
				}finally {
					semaforoTocha.release();
					
				}
				
				if(isTocha == false) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					deslocamento += 2;
				}
			}
			
			if(distanciaPercorrida ==  1500) {
				if(isTocha == true) {
					System.out.println("O cavaleiro "+ cavaleiro + " já pegou a tocha, por isso não pegará a pedra");
					isPedra = false;
				}else if(isTocha == false) {
					try {
						semaforoPedra.acquire();
						System.out.println("O cavaleiro "+ cavaleiro + " pegou a pedra");
						isPedra = true;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						semaforoPedra.release();
					}
					
				
				}	
			}
		}
		
		
		
		
		
	}

}
