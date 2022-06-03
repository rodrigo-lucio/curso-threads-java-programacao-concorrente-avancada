package br.com.lucio.problemaacesso;

import br.com.lucio.servidor.TratadorExecao;

public class ServidorDeTeste {

	/*
	 * Este código pela lógica deveria finalizar sozinho, porém nessas situaçoes a
	 * JVM cria um cache da variavel "estaRodando", e a thread disparada no método
	 * rodar não enxerga a alteração para que consiga encerrar.
	 * 
	 * Por isso existe a o modificador volatile
	 */
	// private boolean estaRodando = false; Utilizando sem o volatile não funciona
	private volatile boolean estaRodando = false;

	// Uma outra alternativa é utilizar os wrappers Atomic, nesse caso utilizaria o
	// AtomicBoolean
	
	/*
	 Outra alternativa que também funcionaria:
	   public synchronized void parar() {
        this.estaRodando = false;
       }
	 */

	public static void main(String[] args) throws InterruptedException {
		ServidorDeTeste servidor = new ServidorDeTeste();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

	private void rodar() {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				System.out.println("Servidor começando, estaRodando = " + estaRodando);

				while (!estaRodando) {
				}
				
				if(estaRodando) {
					throw new RuntimeException("Deu problema na execução da Thread.");
				}

				System.out.println("Servidor rodando, estaRodando = " + estaRodando);

				while (estaRodando) {
				}

				System.out.println("Servidor terminando, estaRodando = " + estaRodando);
			}
		});
		
		//Chamamos essa classe TratadorExecao, quando uma exception nao tratada estoura na thread
		thread.setUncaughtExceptionHandler(new TratadorExecao());
		thread.start();
	}

	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = true");
		estaRodando = true;

		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = false");
		estaRodando = false;
	}
}