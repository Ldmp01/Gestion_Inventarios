package estructuras;

import modelo.Producto;

public class ArbolProductos {

    // Raíz del árbol binario de búsqueda.
    private NodoArbol raiz;

    // Constructor
    public ArbolProductos() {
        this.raiz = null;
    }

    // Inserta un producto en el árbol.
    public void insertarProducto(Producto producto) {
        raiz = insertarRecursivo(raiz, producto);
    }

    // Metodo recursivo para insertar un producto.
    private NodoArbol insertarRecursivo(NodoArbol nodo, Producto producto) {

        // Si el nodo está vacío, se crea uno nuevo.
        if (nodo == null) {
            return new NodoArbol(producto);
        }

        // Compara el nombre del producto con la clave del nodo actual.
        int comparacion = producto.getNombre().compareToIgnoreCase(
                nodo.getProducto().getNombre());

        // Si es menor, se inserta por la izquierda.
        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), producto));
        }

        // Si es mayor, se inserta por la derecha.
        else if (comparacion > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), producto));
        }

        // Si el nombre ya existe, no se inserta nuevamente.

        return nodo;
    }

    // Busca un producto por su nombre.
    public Producto buscarProducto(String nombre) {
        NodoArbol resultado = buscarRecursivo(raiz, nombre);

        if (resultado == null) {
            return null;
        }

        return resultado.getProducto();
    }

    // Metodo recursivo para buscar un producto.
    private NodoArbol buscarRecursivo(NodoArbol nodo, String nombre) {

        // Si no existe el nodo, el producto no fue encontrado.
        if (nodo == null) {
            return null;
        }

        int comparacion = nombre.compareToIgnoreCase(
                nodo.getProducto().getNombre());

        // Si encontró el producto.
        if (comparacion == 0) {
            return nodo;
        }

        // Si el nombre es menor, continúa por la izquierda.
        if (comparacion < 0) {
            return buscarRecursivo(nodo.getIzquierdo(), nombre);
        }

        // Si es mayor, continúa por la derecha.
        return buscarRecursivo(nodo.getDerecho(), nombre);
    }

    // Lista el inventario en orden alfabético.
    public void listarInventario() {
        recorridoEnOrden(raiz);
    }

    // Recorrido EnOrden del árbol.
        private void recorridoEnOrden(NodoArbol nodo) {

        if (nodo != null) {

            recorridoEnOrden(nodo.getIzquierdo());

            System.out.println(nodo.getProducto());

            recorridoEnOrden(nodo.getDerecho());

        }
    }

}