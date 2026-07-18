package estructuras;

import modelo.Producto;

public class Nodo {

    private Producto producto;
    private Nodo siguiente;

    // Constructor
    public Nodo(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public String toString() {
        return producto.toString();
    }
}