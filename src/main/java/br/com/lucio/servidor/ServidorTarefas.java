package br.com.lucio.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;

	public ServidorTarefas() throws IOException {
        System.out.println("Iniciando servidor:");
        this.servidor = new ServerSocket(12345);
        //Esse thread pool cresce/diminui dinamicamente
//        this.threadPool = Executors.newCachedThreadPool(); 
        this.threadPool = Executors.newFixedThreadPool(4, new FabricaDeThreads()); 
        
        //Numero fixo de duas threads no programa
        //ExecutorService threadPool = Executors.newFixedThreadPool(2);
        
        this.estaRodando = new AtomicBoolean(true);
	}
	
    public static void main(String[] args) throws Exception {

    	ServidorTarefas servidor = new ServidorTarefas();
    	servidor.rodar();
    	servidor.parar();
        
    }

	public void rodar() throws IOException {
		while (this.estaRodando.get()) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Cliente conectado na porta: " + socket.getPort());

				// Maneira sem utilizar Thread pool
				// Thread threadCliente = new Thread(new DistribuidorTarefas(socket));
				// threadCliente.start();

				threadPool.execute(new DistribuidorTarefas(threadPool, socket, this));
			} catch (SocketException e) {
				System.out.println("SocketException, está rodando?" + this.estaRodando);
			}
		}
	}
	
    public void parar() throws IOException {
    	this.estaRodando.set(false);
        servidor.close();	
        threadPool.shutdown();
	}


}
