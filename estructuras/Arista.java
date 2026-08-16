package estructuras;

/**
 * Representa una arista del grafo ponderado y NO dirigido.
 *
 * En una lista de adyacencia, cada vértice guarda las aristas que salen de él;
 * por eso una Arista solo necesita conocer el vértice destino y el peso
 * (la distancia). El vértice origen es, implícitamente, el dueño de la lista
 * donde se almacena esta arista.
 *
 * Como el grafo es no dirigido, cada conexión entre dos ubicaciones A y B se
 * representa con DOS aristas recíprocas: una en la adyacencia de A que apunta a
 * B, y otra en la adyacencia de B que apunta a A, ambas con el mismo peso.
 */
public class Arista {

    // Atributos
    private Vertice destino;   // Ubicación a la que conduce esta arista
    private double peso;       // Distancia entre las dos ubicaciones (estática y positiva)

    // Constructor
    public Arista(Vertice destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Getters y Setters
    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "-> " + destino.getUbicacion() + " (" + peso + " km)";
    }
}
