package estructuras;

import modelo.Cliente;

/**
 * Cola de prioridad implementada con inserción ordenada.
 *
 * El frente de la cola siempre contiene al cliente con la prioridad
 * más alta (3 = premium, 2 = afiliado, 1 = básico). En caso de empate
 * de prioridad, se respeta el orden de llegada (FIFO): el que entró
 * primero queda más cerca del frente.
 */
public class ColaClientes {

    private NodoCola frente;
    private int cantidad;

    public ColaClientes() {
        this.frente = null;
        this.cantidad = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Inserción ordenada por prioridad descendente.
     * Recorre la cola hasta encontrar el primer nodo con prioridad
     * ESTRICTAMENTE menor que la del cliente nuevo, y lo inserta ahí.
     * Al usar "estrictamente menor", los empates quedan detrás de los
     * que ya estaban (se conserva el orden de llegada).
     */
    public void encolar(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        NodoCola nuevo = new NodoCola(cliente);

        // Caso 1: la cola está vacía, o el nuevo tiene mayor prioridad
        // que el frente actual, entonces pasa a ser el nuevo frente.
        if (estaVacia() || cliente.getPrioridad() > frente.getCliente().getPrioridad()) {
            nuevo.setSiguiente(frente);
            frente = nuevo;
            cantidad++;
            return;
        }

        // Caso 2: se busca la posición correcta recorriendo la cola.
        NodoCola anterior = frente;
        NodoCola temporal = frente.getSiguiente();

        while (temporal != null && temporal.getCliente().getPrioridad() >= cliente.getPrioridad()) {
            anterior = temporal;
            temporal = temporal.getSiguiente();
        }

        nuevo.setSiguiente(temporal);
        anterior.setSiguiente(nuevo);
        cantidad++;
    }

    /**
     * Extrae y devuelve al cliente del frente, es decir, el de mayor
     * prioridad. Como la cola se mantiene ordenada al insertar, atender
     * es simplemente sacar el primer nodo.
     */
    public Cliente atender() {
        if (estaVacia()) {
            return null;
        }
        NodoCola auxiliar = frente;
        frente = frente.getSiguiente();
        auxiliar.setSiguiente(null);
        cantidad--;
        return auxiliar.getCliente();
    }

    /**
     * Consulta al siguiente cliente a atender sin sacarlo de la cola.
     */
    public Cliente verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.getCliente();
    }

    /**
     * Busca un cliente por su identificación.
     */
    public NodoCola buscar(String identificacion) {
        NodoCola temporal = frente;
        while (temporal != null) {
            if (temporal.getCliente().getIdentificacion().equalsIgnoreCase(identificacion)) {
                return temporal;
            }
            temporal = temporal.getSiguiente();
        }
        return null;
    }

    /**
     * Reporte del estado actual de la cola, del frente hacia el final.
     */
    public String mostrarCola() {
        if (estaVacia()) {
            return "No hay clientes esperando en la cola.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append(" Clientes en espera (").append(cantidad).append(") \n\n");

        NodoCola temporal = frente;
        int posicion = 1;
        while (temporal != null) {
            Cliente c = temporal.getCliente();
            reporte.append(posicion).append(". ").append(c.getNombre())
                    .append(" | Cédula: ").append(c.getIdentificacion())
                    .append(" | Prioridad: ").append(c.getPrioridad())
                    .append(" (").append(c.getTipoCliente()).append(")\n");
            temporal = temporal.getSiguiente();
            posicion++;
        }

        return reporte.toString();
    }
}