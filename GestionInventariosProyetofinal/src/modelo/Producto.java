package modelo;

import java.util.ArrayList;

public class Producto {

    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento;
    private int cantidadCarrito;
    private int cantidadInventario;
    private ArrayList<String> listaImagenes;

    // Constructor
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidadCarrito, int cantidadInventario) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        if (fechaVencimiento == null || fechaVencimiento.trim().isEmpty()) {
            this.fechaVencimiento = "No aplica";
        } else {
            this.fechaVencimiento = fechaVencimiento;
        }
        this.cantidadCarrito = cantidadCarrito;
        this.cantidadInventario = cantidadInventario;
        this.listaImagenes = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidadCarrito() {
        return cantidadCarrito;
    }

    public void setCantidadCarrito(int cantidadCarrito) {
        this.cantidadCarrito = cantidadCarrito;
    }

    public int getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(int cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<String> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    public void agregarImagen(String rutaImagen) {
        this.listaImagenes.add(rutaImagen);
    }

    public String toString() {
        String textoImagenes;
        if (listaImagenes.isEmpty()) {
            textoImagenes = "Ninguna";
        } else {
            textoImagenes = listaImagenes.toString();
        }

        return "Producto: " + nombre + "\nPrecio: ₡" + precio + "\nCategoría: " + categoria +
                "\nVencimiento: " + fechaVencimiento + "\nUnidades en Carrito: " + cantidadCarrito +
                "\nUnidades en Inventario: " + cantidadInventario + "\nRutas de Imágenes: " + textoImagenes;
    }
}