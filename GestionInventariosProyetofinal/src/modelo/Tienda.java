package modelo;

import estructuras.ArbolProductos;
import estructuras.ColaClientes;

public class Tienda {
    private ArbolProductos inventario;
    private ColaClientes filaEspera;
    private String ubicacion;

    public Tienda() {
        this.inventario = new ArbolProductos();
        this.filaEspera = new ColaClientes();
        this.ubicacion = "Tienda Central";
    }

    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getFilaEspera() {
        return filaEspera;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}