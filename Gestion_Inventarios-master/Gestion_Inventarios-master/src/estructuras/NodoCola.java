package estructuras;

import modelo.Cliente;

public class NodoCola {

    private Cliente cliente;
    private NodoCola siguiente;

    // Constructor
    public NodoCola(Cliente cliente) {
        this.cliente = cliente;
        this.siguiente = null;
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }

    public String toString() {
        return cliente.toString();
    }
}