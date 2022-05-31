package br.com.lucio.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	private ServerSocket servidor;
	private ExecutorService threadPool;
	private boolean estaRodando;

	public ServidorTarefas() throws IOException {
        System.out.println("Iniciando servidor:");
        this.servidor = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(); 
        //Esse thread pool cresce/diminui dinamicamente
        
        //Numero fixo de duas threads no programa
        //ExecutorService threadPool = Executors.newFixedThreadPool(2);
        
        this.estaRodando = true;
	}
	
    public static void main(String[] args) throws Exception {

    	ServidorTarefas servidor = new ServidorTarefas();
    	servidor.rodar();
    	servidor.parar();
        
    }

	public void rodar() throws IOException {
		while (this.estaRodando) {
			Socket socket = servidor.accept();
			System.out.println("Cliente conectado na porta: " + socket.getPort());

			// Maneira sem utilizar Thread pool
			// Thread threadCliente = new Thread(new DistribuidorTarefas(socket));
			// threadCliente.start();

			threadPool.execute(new DistribuidorTarefas(socket, this));
		}
	}
	
    public void parar() throws IOException {
    	estaRodando = false;
        servidor.close();	
        threadPool.shutdown();
	}


}
