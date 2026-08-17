package estructuras;

import modelo.Producto;

public class NodoArbol {

    // Atributos
    private Producto producto;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    // Constructor
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return producto.toString();
    }
}