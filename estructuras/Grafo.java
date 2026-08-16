package estructuras;

import java.util.ArrayList;

/**
 * Grafo PONDERADO y NO DIRIGIDO que modela el mapa de entregas.
 *
 * - Los vértices son ubicaciones (la de la Tienda y las de los Clientes).
 * - Las aristas son las calles que unen dos ubicaciones; su peso es la
 *   distancia entre ellas. Como el grafo es no dirigido, cada calle se guarda
 *   en ambos sentidos con el mismo peso.
 *
 * Esta clase se encarga de:
 *   1. Insertar nuevas ubicaciones (vértices) y calles (aristas).
 *   2. Verificar la conectividad, para saber si un Cliente está desconectado
 *      del mapa antes de intentar atenderlo.
 *   3. Ofrecer un mapa básico precargado para poder operar desde el arranque.
 *
 * Nota: el cálculo del camino más corto (Dijkstra) se apoya sobre esta
 * estructura, pero no forma parte de esta clase.
 */
public class Grafo {

    // Único atributo: la colección de vértices (ubicaciones) del mapa.
    private ArrayList<Vertice> vertices;

    // Constructor
    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    // =====================================================================
    // CONSULTAS BÁSICAS
    // =====================================================================

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public int getCantidadVertices() {
        return vertices.size();
    }

    public boolean estaVacio() {
        return vertices.isEmpty();
    }

    /**
     * Busca un vértice por el nombre de su ubicación (ignora mayúsculas/minúsculas).
     * Devuelve el vértice o null si no existe.
     */
    public Vertice buscarVertice(String ubicacion) {
        if (ubicacion == null) {
            return null;
        }
        for (Vertice v : vertices) {
            if (v.getUbicacion().equalsIgnoreCase(ubicacion.trim())) {
                return v;
            }
        }
        return null;
    }

    public boolean existeVertice(String ubicacion) {
        return buscarVertice(ubicacion) != null;
    }

    // =====================================================================
    // INSERCIÓN DE UBICACIONES (VÉRTICES)
    // =====================================================================

    /**
     * Agrega una nueva ubicación al mapa. Es idempotente: si la ubicación ya
     * existe, no la duplica y devuelve el vértice existente. Esto permite que
     * al encolar un Cliente se agregue su ubicación de forma automática sin
     * riesgo de duplicados.
     *
     * @return el vértice creado (o el ya existente), o null si el nombre es inválido.
     */
    public Vertice agregarVertice(String ubicacion) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            return null;
        }
        Vertice existente = buscarVertice(ubicacion);
        if (existente != null) {
            return existente;
        }
        Vertice nuevo = new Vertice(ubicacion.trim());
        vertices.add(nuevo);
        return nuevo;
    }

    // =====================================================================
    // INSERCIÓN DE CALLES (ARISTAS)
    // =====================================================================

    /**
     * Conecta dos ubicaciones con una calle de la distancia indicada.
     * Si alguna de las ubicaciones no existe todavía, se crea automáticamente.
     * Al ser un grafo no dirigido, la conexión se registra en ambos sentidos.
     * Si la calle ya existía, se actualiza su distancia.
     *
     * @return true si la calle se agregó o actualizó; false si los datos son inválidos
     *         (misma ubicación en ambos extremos o distancia no positiva).
     */
    public boolean agregarArista(String origen, String destino, double distancia) {
        if (origen == null || destino == null) {
            return false;
        }
        origen = origen.trim();
        destino = destino.trim();

        // No se permiten lazos (una ubicación consigo misma) ni distancias no positivas.
        if (origen.equalsIgnoreCase(destino) || distancia <= 0) {
            return false;
        }

        Vertice vOrigen = agregarVertice(origen);
        Vertice vDestino = agregarVertice(destino);

        // Si la conexión ya existe, se actualiza el peso en ambos sentidos.
        Arista existenteIda = vOrigen.buscarAdyacencia(vDestino);
        Arista existenteVuelta = vDestino.buscarAdyacencia(vOrigen);
        if (existenteIda != null && existenteVuelta != null) {
            existenteIda.setPeso(distancia);
            existenteVuelta.setPeso(distancia);
            return true;
        }

        // Grafo no dirigido: se agregan las dos aristas recíprocas.
        vOrigen.agregarAdyacencia(new Arista(vDestino, distancia));
        vDestino.agregarAdyacencia(new Arista(vOrigen, distancia));
        return true;
    }

    // =====================================================================
    // VERIFICACIÓN DE CONECTIVIDAD
    // =====================================================================

    /**
     * Determina si existe un camino (directo o indirecto) entre dos ubicaciones,
     * recorriendo el grafo por amplitud (BFS). Como el grafo es no dirigido,
     * "hay camino de A a B" equivale a "A y B están en la misma componente conexa".
     *
     * @return true si se puede llegar de 'origen' a 'destino'; false si alguna
     *         ubicación no existe o están en componentes separadas.
     */
    public boolean hayCamino(String origen, String destino) {
        Vertice inicio = buscarVertice(origen);
        Vertice meta = buscarVertice(destino);

        if (inicio == null || meta == null) {
            return false;
        }
        if (inicio == meta) {
            return true; // el origen y el destino son la misma ubicación
        }

        ArrayList<Vertice> visitados = new ArrayList<>();
        ArrayList<Vertice> cola = new ArrayList<>(); // cola FIFO simple para el BFS
        int frente = 0;

        cola.add(inicio);
        visitados.add(inicio);

        while (frente < cola.size()) {
            Vertice actual = cola.get(frente);
            frente++;

            for (Arista arista : actual.getAdyacencias()) {
                Vertice vecino = arista.getDestino();
                if (vecino == meta) {
                    return true; // se alcanzó el destino
                }
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return false; // se recorrió toda la componente sin encontrar el destino
    }

    /**
     * Verificación pensada para el momento de atender al siguiente Cliente:
     * indica si la ubicación del Cliente está conectada con la de la Tienda,
     * de modo que sea posible calcular una ruta de entrega.
     *
     * Si devuelve false, el Cliente está desconectado del mapa (o su ubicación
     * no existe) y la operación de atención NO debería permitirse.
     */
    public boolean clienteConectadoConTienda(String ubicacionCliente, String ubicacionTienda) {
        return hayCamino(ubicacionCliente, ubicacionTienda);
    }

    /**
     * Indica si una ubicación está aislada: no existe en el mapa, o existe pero
     * no tiene ninguna calle que la conecte con otra ubicación.
     */
    public boolean estaAislada(String ubicacion) {
        Vertice v = buscarVertice(ubicacion);
        return v == null || v.estaAislado();
    }

    // =====================================================================
    // REPORTE / VISUALIZACIÓN DEL MAPA
    // =====================================================================

    /**
     * Devuelve una representación textual del mapa (cada ubicación con las
     * calles que salen de ella y su distancia). Útil para mostrarlo en el menú.
     */
    public String mostrarGrafo() {
        if (estaVacio()) {
            return "El mapa no tiene ninguna ubicación registrada.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("Mapa de ubicaciones (").append(getCantidadVertices()).append(")\n\n");

        for (Vertice v : vertices) {
            reporte.append(v.getUbicacion());
            if (v.estaAislado()) {
                reporte.append("  [aislada, sin conexiones]\n");
            } else {
                reporte.append("\n");
                for (Arista arista : v.getAdyacencias()) {
                    reporte.append("   -> ").append(arista.getDestino().getUbicacion())
                            .append("  (").append(arista.getPeso()).append(" km)\n");
                }
            }
        }
        return reporte.toString();
    }

    // =====================================================================
    // MAPA BÁSICO PRECARGADO
    // =====================================================================

    /**
     * Precarga un mapa básico y CONEXO para que el programa pueda operarse desde
     * el arranque. Todas las ubicaciones quedan comunicadas entre sí, con la
     * "Tienda Central" como ubicación de la Tienda.
     *
     * Uso sugerido al iniciar el programa:
     *     Grafo mapa = new Grafo();
     *     mapa.cargarMapaBasico();
     */
    public void cargarMapaBasico() {
        // Al usar agregarArista, cada ubicación se crea automáticamente la
        // primera vez que aparece, por lo que no hace falta insertarlas aparte.
        agregarArista("Tienda Central", "Barrio Escalante", 2.0);
        agregarArista("Barrio Escalante", "San Pedro", 3.0);
        agregarArista("San Pedro", "Curridabat", 4.0);
        agregarArista("Curridabat", "Zapote", 3.5);
        agregarArista("Zapote", "Tienda Central", 5.0);
        agregarArista("Tienda Central", "La Sabana", 6.0);
        agregarArista("La Sabana", "Escazu", 4.5);
        agregarArista("San Pedro", "Zapote", 2.5);
    }
}
