package ejecucion;

import estructuras.ArbolProductos;
import estructuras.ColaClientes;
import modelo.Cliente;
import modelo.Producto;
import javax.swing.JOptionPane;

public class Main {
    private static ArbolProductos inventarioTienda = new ArbolProductos();
    private static ColaClientes filaEsperaClientes = new ColaClientes();

    static void main(String[] args) {
        inventarioTienda.insertarProducto(new Producto("Arroz", 1200.0, "Granos", "15/12/2027", 0, 50));
        inventarioTienda.insertarProducto(new Producto("Leche", 950.0, "Lácteos", "20/07/2026", 0, 30));
        inventarioTienda.insertarProducto(new Producto("Jabon", 850.0, "Limpieza", "No aplica", 0, 15));
        inventarioTienda.insertarProducto(new Producto("Atun", 1100.0, "Enlatados", "10/05/2028", 0, 40));

        menuPrincipal();
    }

    // Menú Principal del Sistema
    public static void menuPrincipal() {
        String[] opcionesMenu = {
                "1. Gestión de Inventarios",
                "2. Control de Clientes",
                "3. Salir del Sistema"
        };
        String eleccion = "";

        do {
            eleccion = (String) JOptionPane.showInputDialog(
                    null,"Sistema de ventas\nSeleccione la opción que desea ingresar:","Menú Principal",JOptionPane.QUESTION_MESSAGE,null, opcionesMenu, opcionesMenu[0]);

            if (eleccion == null || eleccion.equals("3. Salir del Sistema")) {
                JOptionPane.showMessageDialog(null, "Cerrando la aplicación.", "Sistema Cerrado", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            if (eleccion.equals("1. Gestión de Inventarios")) {
                menuInventario();
            } else if (eleccion.equals("2. Control de Clientes")) {
                menuClientes();
            }

        } while (true);
    }

    // submenú de Inventario utilizando el Árbol Binario de Búsqueda
    private static void menuInventario() {
        String[] opcionesInventario = {
                "1. Insertar Producto al Inventario",
                "2. Buscar Producto en el Inventario",
                "3. Mostrar Inventario",
                "4. Volver al Menú Principal"
        };

        while (true) {
            String eleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Administración del Inventario de la Tienda\nSeleccione la operación:","Módulo de Inventario", JOptionPane.PLAIN_MESSAGE,null, opcionesInventario, opcionesInventario[0]);

            if (eleccion == null || eleccion.equals("4. Volver al Menú Principal")) {
                break;
            }

            if (eleccion.startsWith("1")) registrarProductoEnArbol();
            else if (eleccion.startsWith("2")) buscarProductoEnArbol();
            else if (eleccion.startsWith("3")) {
                System.out.println("\nRecorrido en orden del inventario");
                inventarioTienda.listarInventario();
                JOptionPane.showMessageDialog(null, "El inventario ordenado alfabeticamente ha sido impreso en la Consola del sistema.", "Inventario en lista", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Submenú de Clientes utilizando la Cola de Prioridad y Listas Enlazadas
    private static void menuClientes() {
        String[] opcionesClientes = {
                "1. Registrar Cliente en Fila de Espera",
                "2. Asignar Productos al Carrito de un Cliente",
                "3. Ver Fila de Espera Actual",
                "4. Atender Siguiente Cliente",
                "5. Volver al Menú Principal"
        };

        while (true) {
            String eleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Control de Fila de Espera y Carritos\nSeleccione la operación:",
                    "Módulo de Clientes",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opcionesClientes,
                    opcionesClientes[0]
            );

            if (eleccion == null || eleccion.equals("5. Volver al Menú Principal")) {
                break;
            }

            if (eleccion.startsWith("1")) registrarClienteEnCola();
            else if (eleccion.startsWith("2")) gestionarCarritoCliente();
            else if (eleccion.startsWith("3")) {
                String reporteCola = filaEsperaClientes.mostrarCola();
                JOptionPane.showMessageDialog(null, reporteCola, "Fila de Espera", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (eleccion.startsWith("4")) atenderClientePrioritario();
        }
    }

    private static void registrarProductoEnArbol() {
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo producto:", "Registro Inventario", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null || nombre.trim().isEmpty()) return;

            double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio unitario (₡):", "Registro Inventario", JOptionPane.QUESTION_MESSAGE));
            String categoria = JOptionPane.showInputDialog(null, "Ingrese la categoría del producto:", "Registro Inventario", JOptionPane.QUESTION_MESSAGE);

            int respuestaVencimiento = JOptionPane.showConfirmDialog(null, "¿Este producto posee fecha de vencimiento?", "Registro Inventario", JOptionPane.YES_NO_OPTION);
            String fecha = "No aplica";
            if (respuestaVencimiento == JOptionPane.YES_OPTION) {
                fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de vencimiento (DD/MM/AAAA):", "Registro Inventario", JOptionPane.QUESTION_MESSAGE);
            }

            int cantInventario = Integer.parseInt(JOptionPane.showInputDialog(null, "Cantidad inicial de unidades disponibles en Inventario:", "Registro Inventario", JOptionPane.QUESTION_MESSAGE));

            Producto nuevo = new Producto(nombre, precio, categoria, fecha, 0, cantInventario);
            inventarioTienda.insertarProducto(nuevo);

            JOptionPane.showMessageDialog(null, "Producto insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Datos inválidos o registro cancelado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void buscarProductoEnArbol() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto que desea buscar:", "Buscar", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) return;

        Producto encontrado = inventarioTienda.buscarProducto(nombre);
        if (encontrado != null) {
            JOptionPane.showMessageDialog(null, "Producto Encontrado:\n\n" + encontrado, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El producto '" + nombre + "' no existe en el inventario actual de la tienda.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void registrarClienteEnCola() {
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre completo del cliente:", "Registro Cliente", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null || nombre.trim().isEmpty()) return;

            String identificacion = JOptionPane.showInputDialog(null, "Ingrese el número de identificación:", "Registro Cliente", JOptionPane.QUESTION_MESSAGE);
            if (identificacion == null || identificacion.trim().isEmpty()) return;

            String[] opcionesPrioridad = {"1. Básico", "2. Afiliado", "3. Premium"};
            String seleccionPrioridad = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el nivel de prioridad del cliente:",
                    "Prioridad",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesPrioridad,
                    opcionesPrioridad[0]
            );
            if (seleccionPrioridad == null) return;

            int prioridad = Integer.parseInt(seleccionPrioridad.substring(0, 1));

            Cliente nuevoCliente = new Cliente(nombre, identificacion, prioridad);
            filaEsperaClientes.encolar(nuevoCliente);

            JOptionPane.showMessageDialog(null, "Cliente registrado y acomodado en la fila según su prioridad:\n" + nuevoCliente.getTipoCliente(), "Cliente en cola", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: No se pudo registrar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private static void gestionarCarritoCliente() {
        String cedula = JOptionPane.showInputDialog(null, "Ingrese la cédula del cliente que va a comprar:", "Carrito de Compras", JOptionPane.QUESTION_MESSAGE);
        if (cedula == null || cedula.trim().isEmpty()) return;

        var nodoCliente = filaEsperaClientes.buscar(cedula);
        if (nodoCliente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún cliente en la fila con la cédula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = nodoCliente.getCliente();

        String nombreProd = JOptionPane.showInputDialog(null, "Cliente: " + cliente.getNombre() + "\nIngrese el nombre del producto que desea añadir al carrito:", "Añadir al Carrito", JOptionPane.QUESTION_MESSAGE);
        if (nombreProd == null || nombreProd.trim().isEmpty()) return;

        Producto prodInventario = inventarioTienda.buscarProducto(nombreProd);
        if (prodInventario == null) {
            JOptionPane.showMessageDialog(null, "El producto '" + nombreProd + "' no existe en la tienda.", "Producto Inexistente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidadComprar = Integer.parseInt(JOptionPane.showInputDialog(null, "Producto: " + prodInventario.getNombre() + "\nDisponibles: " + prodInventario.getCantidadInventario() + " unidades.\nIngrese la cantidad a comprar:", "Cantidad", JOptionPane.QUESTION_MESSAGE));

            if (cantidadComprar <= 0) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidadComprar > prodInventario.getCantidadInventario()) {
                JOptionPane.showMessageDialog(null, "Inventario insuficiente. Solo quedan " + prodInventario.getCantidadInventario() + " unidades.", "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            prodInventario.setCantidadInventario(prodInventario.getCantidadInventario() - cantidadComprar);

            Producto productoCarrito = new Producto(
                    prodInventario.getNombre(),
                    prodInventario.getPrecio(),
                    prodInventario.getCategoria(),
                    prodInventario.getFechaVencimiento(),
                    cantidadComprar,
                    0
            );

            cliente.agregarAlCarrito(productoCarrito);
            JOptionPane.showMessageDialog(null, "Se agregaron " + cantidadComprar + " unidades de " + prodInventario.getNombre() + " al carrito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Operación cancelada o formato numérico inválido.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void atenderClientePrioritario() {
        if (filaEsperaClientes.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay clientes en la fila para atender.", "Fila Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Cliente atendido = filaEsperaClientes.atender();

        StringBuilder factura = new StringBuilder();
        factura.append("Factura de compra - Sistema de ventas\n");
        factura.append("Cliente: ").append(atendido.getNombre()).append("\n");
        factura.append("Identificación: ").append(atendido.getIdentificacion()).append("\n");
        factura.append("Tipo de Cliente: ").append(atendido.getTipoCliente()).append("\n");
        factura.append("------------------------------------------------------------\n");

        var carrito = atendido.getCarrito();
        if (carrito.estaVacia()) {
            factura.append("\nEl cliente no adquirió ningún producto.\n");
            factura.append("Total Neto a Pagar: ₡0.00\n");
        } else {
            factura.append("Detalle de Artículos:\n");
            factura.append(carrito.generarReporteCostos());
        }
        factura.append("Cliente procesado exitosamente.");

        JOptionPane.showMessageDialog(null, factura.toString(), "Caja Registradora - Cliente Atendido", JOptionPane.INFORMATION_MESSAGE);
    }
}