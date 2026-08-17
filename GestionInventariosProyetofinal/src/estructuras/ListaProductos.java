package estructuras;

import modelo.Producto;

public class ListaProductos {

    private Nodo primero;

    public ListaProductos() {
        this.primero = null;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public void insertarInicio(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        if (!estaVacia()) {
            nuevo.setSiguiente(primero);
        }
        primero = nuevo;
    }

    public void insertarFinal(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            Nodo temporal = primero;
            while (temporal.getSiguiente() != null) {
                temporal = temporal.getSiguiente();
            }
            temporal.setSiguiente(nuevo);
        }
    }

    public Nodo buscar(String nombre) {
        if (estaVacia()) {
            return null;
        }
        Nodo temporal = primero;
        while (temporal != null) {
            if (temporal.getProducto().getNombre().equalsIgnoreCase(nombre)) {
                return temporal;
            }
            temporal = temporal.getSiguiente();
        }
        return null;
    }

    public Nodo eliminar(String nombre) {
        if (estaVacia()) {
            return null;
        }

        if (primero.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            Nodo auxiliar = primero;
            primero = primero.getSiguiente();
            return auxiliar;
        }

        Nodo temporal = primero;
        Nodo anterior = temporal;

        while (temporal != null && !temporal.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            anterior = temporal;
            temporal = temporal.getSiguiente();
        }

        if (temporal == null) {
            return null;
        }

        anterior.setSiguiente(temporal.getSiguiente());
        return temporal;
    }

    public String generarReporteCostos() {
        if (estaVacia()) {
            return "La lista de productos se encuentra totalmente vacía.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append(" Reporte General De Costos y Totales \n");

        Nodo temporal = primero;
        double acumuladoInventario = 0;
        double acumuladoCarrito = 0;

        while (temporal != null) {
            Producto p = temporal.getProducto();
            double costoInventario = p.getPrecio() * p.getCantidadInventario();
            double costoInventarioReal = p.getPrecio() * p.getCantidadInventario();
            double costoCarrito = p.getPrecio() * p.getCantidadCarrito();

            acumuladoInventario += costoInventarioReal;
            acumuladoCarrito += costoCarrito;

            reporte.append("Producto: ").append(p.getNombre()).append("\n")
                    .append("Precio Unitario: ₡").append(String.format("%.2f", p.getPrecio())).append("\n")
                    .append("Inventario Tienda: ").append(p.getCantidadInventario())
                    .append("unidades (Subtotal: ₡").append(String.format("%.2f", costoInventarioReal)).append(")\n")
                    .append("Carrito Clientes: ").append(p.getCantidadCarrito())
                    .append("unidades (Subtotal: ₡").append(String.format("%.2f", costoCarrito)).append(")\n");

            temporal = temporal.getSiguiente();
        }

        reporte.append("Valor total acumulado en inventario: ₡").append(String.format("%.2f", acumuladoInventario)).append("\n");
        reporte.append("Valor total acumulado en carritos: ₡").append(String.format("%.2f", acumuladoCarrito)).append("\n");

        return reporte.toString();
    }
}