package br.com.lucio.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

	private static int numero = 1;
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "Thread servidor de tarefas " + numero);
		numero++;
		Thread.setDefaultUncaughtExceptionHandler(new TratadorExecao());
		t.setDaemon(true);
		return t;
	}

}
