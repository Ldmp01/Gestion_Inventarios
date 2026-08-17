package estructuras;

public class GrafoMapa {
    private String[] vertices;
    private int[][] matrizAdyacencia;
    private int maxVertices;
    private int cantidadVertices;
    private static final int infinito = 999999;

    public GrafoMapa(int maxVertices) {
        this.maxVertices = maxVertices;
        this.vertices = new String[maxVertices];
        this.matrizAdyacencia = new int[maxVertices][maxVertices];
        this.cantidadVertices = 0;

        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                if (i == j) {
                    matrizAdyacencia[i][j] = 0;
                } else {
                    matrizAdyacencia[i][j] = infinito;
                }
            }
        }
    }

    public void agregarVertice(String nombreUbicacion) {
        if (buscarIndice(nombreUbicacion) == -1 && cantidadVertices < maxVertices) {
            vertices[cantidadVertices] = nombreUbicacion;
            cantidadVertices++;
        }
    }

    public void agregarArista(String origen, String destino, int distancia) {
        int iOr = buscarIndice(origen);
        int iDes = buscarIndice(destino);

        if (iOr != -1 && iDes != -1) {
            matrizAdyacencia[iOr][iDes] = distancia;
            matrizAdyacencia[iDes][iOr] = distancia;
        }
    }

    public int buscarIndice(String nombreUbicacion) {
        for (int i = 0; i < cantidadVertices; i++) {
            if (vertices[i].equalsIgnoreCase(nombreUbicacion)) {
                return i;
            }
        }
        return -1;
    }

    public boolean estaConectado(String ubicacion) {
        int indice = buscarIndice(ubicacion);
        if (indice == -1) return false;

        for (int i = 0; i < cantidadVertices; i++) {
            if (i != indice && matrizAdyacencia[indice][i] != infinito) {
                return true;
            }
        }
        return false;
    }

    // Algoritmo de Dijkstra
    public void calcularRutaDijkstra(String puntoOrigen, String puntoDestino) {
        int inicio = buscarIndice(puntoOrigen);
        int fin = buscarIndice(puntoDestino);

        if (inicio == -1 || fin == -1) {
            System.out.println("Error: Ubicación no encontrada en el sistema.");
            return;
        }

        int[] distancias = new int[cantidadVertices];
        boolean[] visitados = new boolean[cantidadVertices];
        int[] predecesores = new int[cantidadVertices];

        for (int i = 0; i < cantidadVertices; i++) {
            distancias[i] = infinito;
            visitados[i] = false;
            predecesores[i] = -1;
        }

        distancias[inicio] = 0;

        for (int i = 0; i < cantidadVertices - 1; i++) {
            int u = obtenerMinimaDistancia(distancias, visitados);
            if (u == -1) break;

            visitados[u] = true;

            for (int v = 0; v < cantidadVertices; v++) {
                if (!visitados[v] && matrizAdyacencia[u][v] != infinito
                        && distancias[u] != infinito
                        && distancias[u] + matrizAdyacencia[u][v] < distancias[v]) {

                    distancias[v] = distancias[u] + matrizAdyacencia[u][v];
                    predecesores[v] = u;
                }
            }
        }

        imprimirRutaFinal(distancias[fin], predecesores, inicio, fin);
    }

    private int obtenerMinimaDistancia(int[] distancias, boolean[] visitados) {
        int min = infinito;
        int indiceMin = -1;

        for (int v = 0; v < cantidadVertices; v++) {
            if (!visitados[v] && distancias[v] <= min) {
                min = distancias[v];
                indiceMin = v;
            }
        }
        return indiceMin;
    }

    private void imprimirRutaFinal(int distanciaTotal, int[] predecesores, int inicio, int fin) {
        if (distanciaTotal == infinito) {
            System.out.println("No hay ruta disponible.");
            return;
        }

        int[] caminoInvertido = new int[cantidadVertices];
        int contadorPasos = 0;
        int actual = fin;

        while (actual != -1) {
            caminoInvertido[contadorPasos] = actual;
            contadorPasos++;
            actual = predecesores[actual];
        }

        System.out.print("Camino más corto: ");
        for (int i = contadorPasos - 1; i >= 0; i--) {
            System.out.print(vertices[caminoInvertido[i]]);
            if (i > 0) System.out.print(" -> ");
        }
        System.out.println("\nDistancia total: " + distanciaTotal + " km");
    }
}