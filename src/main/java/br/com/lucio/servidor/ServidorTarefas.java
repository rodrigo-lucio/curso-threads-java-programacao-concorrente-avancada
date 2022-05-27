package br.com.lucio.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

    public static void main(String[] args) throws Exception {

        System.out.println("Iniciando servidor:");
        ServerSocket servidor = new ServerSocket(12346);
        
        //Numero fixo de duas threads no programa
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        //Esse thread pool cresce/diminui dinamicamente
        //ExecutorService threadPool = Executors.newCachedThreadPool();
        
        while(true) {
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado na porta: " + socket.getPort());
            
            //Maneira sem utilizar Thread pool
            //Thread threadCliente = new Thread(new DistribuidorTarefas(socket));
            //threadCliente.start();
            
            threadPool.execute(new DistribuidorTarefas(socket));
        }
        
    }

}
