package br.com.lucio.servidor;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable {

    private BlockingQueue<String> filaComandos;
    
    public TarefaConsumir(BlockingQueue<String> filaComandos) {
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {
        try {
            // Consome o comando da fila
            String take = null;
            while((take = this.filaComandos.take()) != null) {
                System.out.println("Consumindo comando " + take + ". Thread: " + Thread.currentThread().getName());
                Thread.sleep(5000);
            }
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }   

}
