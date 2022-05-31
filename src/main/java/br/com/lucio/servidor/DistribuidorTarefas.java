package br.com.lucio.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuidorTarefas implements Runnable {

    private Socket socket;

    public DistribuidorTarefas(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            
            System.out.println("Distribuindo tarefa cliente na porta: " + this.socket);
            
            PrintStream saidaParaCliente = new PrintStream(socket.getOutputStream()) ;
            
            Scanner scanner = new Scanner(socket.getInputStream());
            while(scanner.hasNextLine()) {
            	String comando = scanner.nextLine();
            	System.out.println("Comando enviado pelo cliente:" + comando);
                
            	switch (comando) {
				case "c1":
					saidaParaCliente.println("Confirmação comando " + comando);
					break;
					
				case "c2":
					saidaParaCliente.println("Confirmação comando " + comando);
					break;

				default:
					saidaParaCliente.println("Confirmação " + comando +  " nãoo encontrado");
					break;
				}
                
            }
            
            saidaParaCliente.close();
            
//            Thread.sleep(5000);
            
            System.out.println("Cliente: " + socket + " encerrado");
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
