package modelo;

import estructuras.ListaProductos;

public class Cliente {

    public static final int PRIORIDAD_BASICO = 1;
    public static final int PRIORIDAD_AFILIADO = 2;
    public static final int PRIORIDAD_PREMIUM = 3;

    private String nombre;
    private String identificacion;
    private int prioridad;
    private ListaProductos carrito;

    // Constructor
    public Cliente(String nombre, String identificacion, int prioridad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        setPrioridad(prioridad);
        this.carrito = new ListaProductos();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Solo acepta valores entre 1 y 3. Cualquier otro valor
     * se ajusta al mínimo (1 = cliente básico).
     */
    public void setPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_BASICO || prioridad > PRIORIDAD_PREMIUM) {
            this.prioridad = PRIORIDAD_BASICO;
        } else {
            this.prioridad = prioridad;
        }
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    public void setCarrito(ListaProductos carrito) {
        this.carrito = carrito;
    }

    /**
     * Agrega un producto al carrito personal del cliente.
     */
    public void agregarAlCarrito(Producto producto) {
        if (producto != null) {
            this.carrito.insertarFinal(producto);
        }
    }

    /**
     * Devuelve el nombre del tipo de cliente según su prioridad.
     */
    public String getTipoCliente() {
        if (prioridad == PRIORIDAD_PREMIUM) {
            return "Premium";
        } else if (prioridad == PRIORIDAD_AFILIADO) {
            return "Afiliado";
        } else {
            return "Básico";
        }
    }

    public String toString() {
        return "Cliente: " + nombre +
                "\nIdentificación: " + identificacion +
                "\nPrioridad: " + prioridad + " (" + getTipoCliente() + ")";
    }
}