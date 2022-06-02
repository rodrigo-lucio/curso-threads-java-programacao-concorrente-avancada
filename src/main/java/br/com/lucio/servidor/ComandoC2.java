package br.com.lucio.servidor;

import java.io.PrintStream;

public class ComandoC2 implements Runnable {

	private PrintStream saidaCliente;

	public ComandoC2(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
		
	}

	@Override
	public void run() {
		
		System.out.println("Executando " + this.getClass().getSimpleName());
		
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		saidaCliente.println("Comando " + this.getClass().getSimpleName() + " executado");
		
	}

}
