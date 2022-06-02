package br.com.lucio.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

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
						saidaCliente.println("Confirmação comando " + comando);
						ComandoC1 c1 = new ComandoC1(saidaCliente);
						this.threadPool.execute(c1);
						break;
	
					case "c2":
						saidaCliente.println("Confirmação comando " + comando);
						ComandoC2 c2 = new ComandoC2(saidaCliente);
						this.threadPool.execute(c2);
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

}
