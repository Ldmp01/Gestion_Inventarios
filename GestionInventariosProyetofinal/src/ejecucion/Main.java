package ejecucion;

import estructuras.GrafoMapa;
import modelo.Cliente;
import modelo.Producto;
import modelo.Tienda;

import java.util.Scanner;

public class Main {

    private static Tienda tienda = new Tienda();
    private static GrafoMapa grafoMapa = new GrafoMapa(20);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        inicializarMapa();
        tienda.getFilaEspera().setGrafo(grafoMapa);
        precargarInventario();

        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            try {
                System.out.print("Ingrese una opcion: ");
                opcion = Integer.parseInt(scanner.nextLine());
                System.out.println();
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida. Intente de nuevo.\n");
            }
        }
    }

    private static void inicializarMapa() {
        String ubicacionTienda = tienda.getUbicacion();

        grafoMapa.agregarVertice(ubicacionTienda);
        grafoMapa.agregarVertice("San Jose");
        grafoMapa.agregarVertice("Heredia");
        grafoMapa.agregarVertice("Alajuela");
        grafoMapa.agregarVertice("Cartago");

        grafoMapa.agregarArista(ubicacionTienda, "San Jose", 5);
        grafoMapa.agregarArista(ubicacionTienda, "Heredia", 10);
        grafoMapa.agregarArista("San Jose", "Cartago", 15);
        grafoMapa.agregarArista("Heredia", "Alajuela", 12);
        grafoMapa.agregarArista("San Jose", "Heredia", 8);
    }

    private static void precargarInventario() {
        tienda.getInventario().insertarProducto(new Producto("Arroz", 1200, "Abarrotes", "2027-12-31", 0, 50));
        tienda.getInventario().insertarProducto(new Producto("Leche", 900, "Lacteos", "2026-09-15", 0, 30));
        tienda.getInventario().insertarProducto(new Producto("Cafe", 2500, "Abarrotes", "2027-05-20", 0, 20));
    }

    private static void mostrarMenu() {
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Atender cliente");
        System.out.println("3. Mostrar clientes en cola");
        System.out.println("4. Mostrar inventario");
        System.out.println("5. Agregar ubicacion al mapa");
        System.out.println("6. Agregar conexion de calles");
        System.out.println("0. Salir");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarCliente();
                break;
            case 2:
                atenderCliente();
                break;
            case 3:
                System.out.println(tienda.getFilaEspera().mostrarCola());
                break;
            case 4:
                System.out.println("--- INVENTARIO ---");
                tienda.getInventario().listarInventario();
                break;
            case 5:
                agregarUbicacion();
                break;
            case 6:
                agregarConexion();
                break;
            case 0:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opcion no valida.");
        }
        System.out.println();
    }

    private static void registrarCliente() {
        System.out.println("--- REGISTRO DE CLIENTE ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Cedula: ");
        String identificacion = scanner.nextLine();

        System.out.print("Ubicacion: ");
        String ubicacion = scanner.nextLine();

        System.out.println("Tipo de cliente (1. Basico | 2. Afiliado | 3. Premium): ");
        System.out.print("Seleccione tipo: ");
        int tipo = Integer.parseInt(scanner.nextLine());

        String tipoCliente = "Basico";
        int prioridad = 1;

        if (tipo == 2) {
            tipoCliente = "Afiliado";
            prioridad = 2;
        } else if (tipo == 3) {
            tipoCliente = "Premium";
            prioridad = 3;
        }

        Cliente nuevoCliente = new Cliente(nombre, identificacion, ubicacion, prioridad, tipoCliente);
        tienda.getFilaEspera().encolar(nuevoCliente);

        System.out.println("Cliente registrado correctamente.");
    }

    private static void atenderCliente() {
        if (tienda.getFilaEspera().estaVacia()) {
            System.out.println("No hay clientes en espera.");
            return;
        }

        Cliente cliente = tienda.getFilaEspera().verFrente();
        String ubicacionCliente = cliente.getUbicacion();

        if (!grafoMapa.estaConectado(ubicacionCliente)) {
            System.out.println("Atencion: La ubicacion '" + ubicacionCliente + "' no tiene conexiones registradas en el mapa.");
            System.out.println("Agregue una conexion antes de procesar este cliente.");
            return;
        }

        cliente = tienda.getFilaEspera().atender();

        System.out.println("--- FACTURA DE COMPRA ---");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Cedula: " + cliente.getIdentificacion());
        System.out.println("Tipo: " + cliente.getTipoCliente() + " (Prioridad " + cliente.getPrioridad() + ")");
        System.out.println("Origen: " + tienda.getUbicacion());
        System.out.println("Destino: " + cliente.getUbicacion());
        System.out.println();
        System.out.println("Ruta de entrega:");
        grafoMapa.calcularRutaDijkstra(tienda.getUbicacion(), cliente.getUbicacion());
    }

    private static void agregarUbicacion() {
        System.out.print("Nombre de la nueva ubicacion: ");
        String nombre = scanner.nextLine();
        grafoMapa.agregarVertice(nombre);
        System.out.println("Ubicacion agregada correctamente.");
    }

    private static void agregarConexion() {
        System.out.print("Ubicacion origen: ");
        String origen = scanner.nextLine();

        System.out.print("Ubicacion destino: ");
        String destino = scanner.nextLine();

        System.out.print("Distancia en km: ");
        int distancia = Integer.parseInt(scanner.nextLine());

        grafoMapa.agregarArista(origen, destino, distancia);
        System.out.println("Conexion agregada correctamente.");
    }
}