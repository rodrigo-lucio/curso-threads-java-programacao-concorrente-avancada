package br.com.lucio.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

	private PrintStream saidaCliente;

	public ComandoC2ChamaWS(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;

	}

	@Override
	public String call() throws InterruptedException {

		System.out.println("Executando " + this.getClass().getSimpleName());

		Thread.sleep(15000);

		int numeroAleatorio = new Random().nextInt(10000);
		saidaCliente.println("Comando " + this.getClass().getSimpleName() + " executado");
		System.out.println("Comando " + this.getClass().getSimpleName() + " executado");
		
		return "Resposta numero aleatorio de " + this.getClass().getSimpleName() + ": " + String.valueOf(numeroAleatorio);
	}

}
