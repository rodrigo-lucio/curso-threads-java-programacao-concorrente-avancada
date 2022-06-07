package br.com.lucio.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream saidaCliente;

	public ComandoC1(PrintStream saidaCliente) {
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
		System.out.println("Comando " + this.getClass().getSimpleName() + " executado");
		
//		Ao lançar essa exception, cairia tudo na Nossa fabrica de threads, que criou o Tratador de exeção para exeptions inesperadas
//		throw new RuntimeException("problema ");
		
	}

}
