package br.com.lucio.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuidorTarefas implements Runnable {

    private Socket socket;
	private ServidorTarefas servidorTarefas;
	private ExecutorService threadPool;

    public DistribuidorTarefas(ExecutorService threadPool, Socket socket, ServidorTarefas servidorTarefas) {
        this.threadPool = threadPool;
		this.socket = socket;
		this.servidorTarefas = servidorTarefas;
    }

    @Override
    public void run() {
        try {
            
            System.out.println("Distribuindo tarefa cliente na porta: " + this.socket);
            
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream()) ;
            
            Scanner scanner = new Scanner(socket.getInputStream());
            while(scanner.hasNextLine()) {
            	String comando = scanner.nextLine();
            	System.out.println("Comando enviado pelo cliente:" + comando);
                
				switch (comando) {
					case "c1":
						comandoC1(saidaCliente, comando);
						break;
	
					case "c2":
						comandoC2(saidaCliente, comando);
						break;
	
					case "fim":
						saidaCliente.println("Desligando o servidor");
						servidorTarefas.parar();
						break;
	
					default:
						saidaCliente.println("Comando " + comando + " não encontrado");
						break;
				}
                
            }
            
            saidaCliente.close();
            
//            Thread.sleep(5000);
            
            System.out.println("Cliente: " + socket + " encerrado");
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	private void comandoC1(PrintStream saidaCliente, String comando) {
		saidaCliente.println("Confirmação comando " + comando);
		ComandoC1 c1 = new ComandoC1(saidaCliente);
		this.threadPool.execute(c1);
	}


	private void comandoC2(PrintStream saidaCliente, String comando) {
		//Com as classes C2 implementando o Callable, conseguimos ter um retorno da thread
		
		saidaCliente.println("Confirmação comando " + comando);
		ComandoC2ChamaWS c2ws = new ComandoC2ChamaWS(saidaCliente);
		ComandoC2AcessaBanco c2bd = new ComandoC2AcessaBanco(saidaCliente);

		Future<String> futureWS = this.threadPool.submit(c2ws);
		Future<String> futureBD = this.threadPool.submit(c2bd);
		
		this.threadPool.submit(new AguardaResultadosFutureWSFutureBancoDados(futureWS, futureBD, saidaCliente));
		
	}


}
