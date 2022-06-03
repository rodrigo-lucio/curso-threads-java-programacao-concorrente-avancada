package br.com.lucio.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AguardaResultadosFutureWSFutureBancoDados implements Callable<Void> {

	private Future<String> futureWS;
	private Future<String> futureBD;
	private PrintStream saidaCliente;

	public AguardaResultadosFutureWSFutureBancoDados(Future<String> futureWS, Future<String> futureBD,
			PrintStream saidaCliente) {
				this.futureWS = futureWS;
				this.futureBD = futureBD;
				this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() {

		System.out.println("Aguardando resultados Future WS e FutureBanco");
		
		try {
			//15 segundos de timeout
			//Aqui fica travado esperando
			String resultadoWS = futureWS.get(20, TimeUnit.SECONDS);
			String resultadoBD = futureBD.get(20, TimeUnit.SECONDS);
			
			System.out.println("Resultado WS:" + resultadoWS);
			System.out.println("Resultado BD:" + resultadoBD);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			saidaCliente.println("Timeout na execução do comando c2");
			System.out.println("Timeout na execução do comando c2");
			
			//Cancela a execução duas threads, por ventura apenas uma pode ocorrer timeout
			this.futureWS.cancel(true);
			this.futureBD.cancel(true);
		}
		
		return null;
		
	}

}
