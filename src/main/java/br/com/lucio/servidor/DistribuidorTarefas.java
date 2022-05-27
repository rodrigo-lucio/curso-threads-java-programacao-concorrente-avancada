package br.com.lucio.servidor;

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
            
            Scanner scanner = new Scanner(socket.getInputStream());
            while(scanner.hasNextLine()) {
                System.out.println("Comando enviado pelo cliente:" + scanner.nextLine());
            }
            
//            Thread.sleep(5000);
            
            System.out.println("Cliente: " + socket + " encerrado");
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
