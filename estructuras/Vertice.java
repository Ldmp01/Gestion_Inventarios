package estructuras;

import java.util.ArrayList;

/**
 * Representa un vértice del grafo, es decir, una UBICACIÓN del mapa
 * (la de la Tienda o la de un Cliente).
 *
 * Cada vértice se identifica por el nombre de su ubicación (único dentro del
 * grafo) y mantiene su lista de adyacencia: las aristas que lo conectan con
 * otras ubicaciones.
 */
public class Vertice {

    // Atributos
    private String ubicacion;               // Nombre único de la ubicación (clave del vértice)
    private ArrayList<Arista> adyacencias;  // Aristas (calles) que salen de esta ubicación

    // Constructor
    public Vertice(String ubicacion) {
        this.ubicacion = ubicacion;
        this.adyacencias = new ArrayList<>();
    }

    // Getters y Setters
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ArrayList<Arista> getAdyacencias() {
        return adyacencias;
    }

    /**
     * Agrega una arista a la lista de adyacencia de este vértice.
     */
    public void agregarAdyacencia(Arista arista) {
        this.adyacencias.add(arista);
    }

    /**
     * Devuelve la arista que conecta este vértice con el destino indicado,
     * o null si no existe una conexión directa entre ambos.
     */
    public Arista buscarAdyacencia(Vertice destino) {
        for (Arista arista : adyacencias) {
            if (arista.getDestino() == destino) {
                return arista;
            }
        }
        return null;
    }

    /**
     * Indica si este vértice no tiene ninguna calle conectada (está aislado).
     */
    public boolean estaAislado() {
        return adyacencias.isEmpty();
    }

    @Override
    public String toString() {
        return ubicacion;
    }
}
