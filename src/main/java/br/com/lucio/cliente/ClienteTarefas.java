package br.com.lucio.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 12346);
        System.out.println("Conex�o estabelecida");

        Thread enviaComando = new Thread(() -> {

            PrintStream saida;
            try {
                saida = new PrintStream(socket.getOutputStream());

                Scanner teclado = new Scanner(System.in);

                System.out.println("Informe uma mensagem para enviar para o servidor:");
                while (teclado.hasNextLine()) {
                    String linha = teclado.nextLine();
                    if (linha.trim().equals("")) {
                        break;
                    }
                    saida.println(linha);
                }

                saida.close();
                teclado.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }); 

        Thread recebeRespostaServidor = new Thread(() -> {
            System.out.println("Obtendo resposta do servidor:");
            Scanner respostaServidor;
            try {
                respostaServidor = new Scanner(socket.getInputStream());

                while (respostaServidor.hasNextLine()) {
                    System.out.println(respostaServidor.nextLine());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        enviaComando.start();
        recebeRespostaServidor.start();
        
        //Dizemos para a Thread main se juntar a essa thread envia comando, e s� ap�s ela encerrar, o socket.close � executado
        enviaComando.join();
        System.out.println("Conex�o encerrada");
        socket.close();

    }
}
