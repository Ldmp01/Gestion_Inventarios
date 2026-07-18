package ejecucion;

import estructuras.ListaProductos;
import estructuras.Nodo;
import modelo.Producto;
import javax.swing.JOptionPane;

public class Main {

    private static ListaProductos miLista = new ListaProductos();

    public static void main(String[] args) {
        miLista.insertarFinal(new Producto("Arroz", 1200.0, "Granos", "15/12/2027", 2, 50));
        miLista.insertarFinal(new Producto("Leche", 950.0, "Lácteos", "20/07/2026", 3, 30));
        miLista.insertarInicio(new Producto("Jabon", 850.0, "Limpieza", "No aplica", 1, 15));

        menuPrincipal();
    }

    // Menú Principal
    public static void menuPrincipal() {
        String[] opcionesMenu = {"1. Gestión de Inventarios", "2. Clientes", "3. Salir del Sistema"};
        String eleccion = "";

        do {
            eleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Bienvenido al Sistema de Ventas de Productos" +
                            "\nSeleccione el módulo al que desea ingresar:",
                    "Menú principal del sistema",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesMenu,
                    opcionesMenu[0]
            );

            if (eleccion == null || eleccion.equals("3. Salir del Sistema")) {
                JOptionPane.showMessageDialog(null, "Cerrando la aplicación.", "Sistema Cerrado", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            if (eleccion.equals("1. Gestión de Inventarios")) {
                menuInventario();
            } else if (eleccion.equals("2. Clientes")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Sección en construcción.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } while (eleccion != null && !eleccion.equals("3. Salir del Sistema"));
    }

    // Menú Inventario
    private static void menuInventario() {
        String[] opcionesInventario = {
                "1. Insertar Producto al Inicio",
                "2. Insertar Producto al Final",
                "3. Modificar Datos de un Producto",
                "4. Añadir Ruta de Imagen",
                "5. Eliminar un Producto",
                "6. Imprimir Reporte de Costos",
                "7. Volver al Menú Principal"
        };

        while (true) {
            String eleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Gestión de inventarios" +
                            "\nSeleccione la operación que desea realizar:",
                    "Administración de inventarios",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opcionesInventario,
                    opcionesInventario[0]
            );

            if (eleccion == null || eleccion.equals("7. Volver al Menú Principal")) {
                break;
            }

            if (eleccion.startsWith("1")) registrarProducto(true);
            else if (eleccion.startsWith("2")) registrarProducto(false);
            else if (eleccion.startsWith("3")) modificarProducto();
            else if (eleccion.startsWith("4")) agregarImagenAProducto();
            else if (eleccion.startsWith("5")) eliminarProducto();
            else if (eleccion.startsWith("6")) {
                String reporte = miLista.generarReporteCostos();
                JOptionPane.showMessageDialog(null, reporte, "Reporte de Costos", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private static void registrarProducto(boolean alInicio) {
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo producto:", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null || nombre.trim().isEmpty()) return;

            double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio unitario (₡):", "Registro", JOptionPane.QUESTION_MESSAGE));
            String categoria = JOptionPane.showInputDialog(null, "Ingrese la categoría del producto:", "Registro", JOptionPane.QUESTION_MESSAGE);

            int respuestaVencimiento = JOptionPane.showConfirmDialog(null, "¿Este producto posee fecha de vencimiento?", "Registro", JOptionPane.YES_NO_OPTION);
            String fecha = "No aplica";
            if (respuestaVencimiento == JOptionPane.YES_OPTION) {
                fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de vencimiento (DD/MM/AAAA):", "Registro", JOptionPane.QUESTION_MESSAGE);
            }

            int cantCarrito = Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad inicial de unidades asignadas al Carrito del Cliente:", "Registro", JOptionPane.QUESTION_MESSAGE));
            int cantInventario = Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad inicial de unidades disponibles en Inventario de Tienda:", "Registro", JOptionPane.QUESTION_MESSAGE));

            Producto nuevo = new Producto(nombre, precio, categoria, fecha, cantCarrito, cantInventario);
            if (alInicio) {
                miLista.insertarInicio(nuevo);
            } else {
                miLista.insertarFinal(nuevo);
            }
            JOptionPane.showMessageDialog(null, "Producto registrado correctamente en la lista dinámica.", "Listo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Entrada de datos no válida. Registro cancelado.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void modificarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto que desea modificar:", "Modificación", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null) return;

            Nodo encontrado = miLista.buscar(nombre);
            if (encontrado == null) {
                JOptionPane.showMessageDialog(null, "El producto '" + nombre + "' no existe en los registros actuales.", "Producto inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto p = encontrado.getProducto();
            double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog(null, "Precio actual: ₡" + p.getPrecio() + "\nIngrese nuevo precio:", "Modificación", JOptionPane.QUESTION_MESSAGE));
            String nuevaCategoria = JOptionPane.showInputDialog(null, "Categoría actual: " + p.getCategoria() + "\nIngrese nueva categoría:", "Modificación", JOptionPane.QUESTION_MESSAGE);
            int nuevoCarrito = Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad carrito actual: " + p.getCantidadCarrito() + "\nIngrese nueva cantidad carrito:", "Modificación", JOptionPane.QUESTION_MESSAGE));
            int nuevoInventario = Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad inventario actual: " + p.getCantidadInventario() + "\nIngrese nueva cantidad inventario:", "Modificación", JOptionPane.QUESTION_MESSAGE));

            p.setPrecio(nuevoPrecio);
            p.setCategoria(nuevaCategoria);
            p.setCantidadCarrito(nuevoCarrito);
            p.setCantidadInventario(nuevoInventario);

            JOptionPane.showMessageDialog(null, "Los datos de '" + nombre + "' se actualizaron con éxito.", "Listo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Modificación cancelada debido a valores de entrada erróneos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void agregarImagenAProducto() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto para añadir una imagen:", "Añadir imagen", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return;

        Nodo encontrado = miLista.buscar(nombre);
        if (encontrado == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el producto en los registros.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ruta = JOptionPane.showInputDialog(null, "Ingrese la ruta interna del proyecto (ej: imagenes/arroz.png):", "Añadir imagen", JOptionPane.QUESTION_MESSAGE);
        if (ruta != null && !ruta.trim().isEmpty()) {
            encontrado.getProducto().agregarImagen(ruta);
            JOptionPane.showMessageDialog(null, "La ruta se asignó correctamente al producto.", "Listo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void eliminarProducto() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto que desea eliminar definitivamente:", "Eliminación de Producto", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return;

        Nodo eliminado = miLista.eliminar(nombre);
        if (eliminado != null) {
            JOptionPane.showMessageDialog(null, "El producto fue eliminado:\n\n" + eliminado.getProducto(), "Listo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la eliminación porque el producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}