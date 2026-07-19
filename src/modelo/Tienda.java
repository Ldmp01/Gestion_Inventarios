package modelo;

import estructuras.ArbolProductos;
import estructuras.ColaClientes;

public class Tienda {
    private ArbolProductos inventario;
    private ColaClientes filaEspera;

    public Tienda() {
        this.inventario = new ArbolProductos();
        this.filaEspera = new ColaClientes();
    }

    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getFilaEspera() {
        return filaEspera;
    }
}