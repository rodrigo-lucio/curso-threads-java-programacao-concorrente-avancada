package br.com.lucio.servidor;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorExecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {

		System.out.println("Deu pau na thread " + t.getName() + ". Erro: " + e);
		 
	}

}
