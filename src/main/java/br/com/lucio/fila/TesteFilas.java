package br.com.lucio.fila;

import java.util.LinkedList;
import java.util.Queue;

public class TesteFilas {

    public static void main(String[] args) {
        
        Queue<String> fila = new LinkedList<>();
        
        fila.add("c1");
        fila.add("c1");
        fila.add("c2");
        fila.add("c3");

        System.out.println(fila.remove("c1"));
        System.out.println(fila.remove("c1"));
        System.out.println(fila.remove());
        System.out.println(fila.poll());
        System.out.println(fila.poll());

    }
}
