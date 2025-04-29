package exp3_s7_benjamin_ignacio_ruiz_miranda;

/**
 * @author Darklight
 */

import java.util.ArrayList;
import java.util.Scanner;

class Venta {
    String tipoAsiento;
    int valorAsiento;
    int valorDescuento;
    int numeroAsiento;

    public Venta(String tipoAsiento, int valorAsiento, int valorDescuento, int numeroAsiento) {
        this.tipoAsiento = tipoAsiento;
        this.valorAsiento = valorAsiento;
        this.valorDescuento = valorDescuento;
        this.numeroAsiento = numeroAsiento;
    }

}

public class Exp3_S7_Benjamin_Ignacio_Ruiz_Miranda {

    // Variables "globales"... (aun las estoy tratando de entender)
    static String nombreTeatro = "Teatro Moro";
    static boolean[] filaASientosVIP = new boolean[10];
    static boolean[] filaASientosPlatea = new boolean[10];
    static boolean[] filaASientosBalcon = new boolean[10];
    static int descuentoEstudiante = 10;
    static int descuentoTerceraEdad = 15;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Venta> Ventas = new ArrayList<>();
        boolean salir = false;

        do { // Menu principal

            System.out.println("Bienvenido al sistema de ventas del " + nombreTeatro);
            
            String mensajeMenuPrincipal = "Escriba la opcion del menu que quiera realizar:\n1.- Venta de entradas\n2.- Resumen de ventas\n3.- Calcular Ingresos Totales\n4.- Salir del programa";
            int opcion = leerOpcionValida(sc, 1, 4, mensajeMenuPrincipal);

            switch (opcion) {
                case 1 -> sistemaVentas(Ventas, sc);
                case 2 -> sistemaResumen(Ventas);
                case 3 -> sistemaIngresosTotales(Ventas);
                case 4 -> {
                        System.out.println("Gracias por su compra");
                        salir = true;
                        }
            }

        } while (!salir);


    }

    public static int checkAsientosDisponiblesVIP() { // 3 Metodos similares que cuentan cuantos asiento hay en cada categoria
        int asientosDisponibles = 0;
        for (int i = 0; i < filaASientosVIP.length; i++) {
            if (filaASientosVIP[i] == false) {
            asientosDisponibles++;
            }

    }
        return asientosDisponibles;
}

    public static int checkAsientosDisponiblesPlatea() {
        int asientosDisponibles = 0;
        for (int i = 0; i < filaASientosPlatea.length; i++) {
            if (filaASientosPlatea[i] == false) {
            asientosDisponibles++;
            }

    }
        return asientosDisponibles;
    }

    public static int checkAsientosDisponiblesBalcon() {
        int asientosDisponibles = 0;
        for (int i = 0; i < filaASientosBalcon.length; i++) {
            if (filaASientosBalcon[i] == false) {
            asientosDisponibles++;
            }

    }
        return asientosDisponibles;
    }

    public static void sistemaResumen(ArrayList<Venta> Ventas) { // Metodo para la opcion 2 del menu principal

        if (Ventas.isEmpty()) {
            System.out.println("No hay asientos comprados.");
            return;
        }
        
        for (int i = 0; i < Ventas.size(); i++) {
            System.out.println("\n-- COMPRA #" + (i+1) + " --");
            System.out.println("Asiento: " + Ventas.get(i).numeroAsiento + " de tipo: " + Ventas.get(i).tipoAsiento);
            System.out.println("Costo base: " + Ventas.get(i).valorAsiento);
            System.out.println("Descuento aplicado: " + Ventas.get(i).valorDescuento + "%");
            System.out.println("Costo final: " + ((Ventas.get(i).valorAsiento) - 
                                                  ((Ventas.get(i).valorAsiento) * 
                                                   (Ventas.get(i).valorDescuento)) / 100));
            System.out.println("");
        }
    }

    public static void sistemaIngresosTotales(ArrayList<Venta> Ventas) { // Metodo para la opcion 3 del menu principal
        int totalIngresos = 0;

        for (int i = 0; i < Ventas.size(); i++) {
            totalIngresos += ((Ventas.get(i).valorAsiento) - 
                            ((Ventas.get(i).valorAsiento) * 
                            (Ventas.get(i).valorDescuento)) / 100);
        }

        System.out.println("El total de ingresos generado es de: " + totalIngresos);
    }

    public static void sistemaVentas (ArrayList<Venta> Ventas, Scanner sc) { // Metodo para la opcion 1 del menu principal
                                                                             // Se usan los metodos de checkAsientosDisponibles para ver si hay asientos disponibles
        System.out.println("-- Usted esta comprando una entrada --");
        System.out.println("Asientos VIP Disponibles: " + checkAsientosDisponiblesVIP());
        System.out.println("Asientos Platea Disponibles: " + checkAsientosDisponiblesPlatea());
        System.out.println("Asientos Balcon Disponibles: " + checkAsientosDisponiblesBalcon());
        System.out.println();
        String mensajeSeleccionAsiento = "Seleccione que tipo de asiento desea comprar\n1.- Asiento VIP\n2.- Asiento Platea\n3.- Asiento Balcon";
        int opcion = leerOpcionValida(sc, 1, 3, mensajeSeleccionAsiento);

        switch (opcion) {
            case 1 -> reservarAsiento(Ventas, sc,
                                     filaASientosVIP, "VIP", 13500);
            case 2 -> reservarAsiento(Ventas, sc,
                                     filaASientosPlatea, "Platea", 9500);
            case 3 -> reservarAsiento(Ventas, sc,
                                     filaASientosBalcon, "Balcón", 7000);
        }
    }

    public static void reservarAsiento(ArrayList<Venta> Ventas, Scanner sc, boolean[] fila, String tipo, int precioBase) { // Metodo para reservar asientos
        // Busca el primer índice libre en la fila de asientos.
        // El bucle se detiene tan pronto como encuentra el primer asiento disponible.
        int asiento = -1;
        for (int i = 0; i < fila.length; i++) {
            if (!fila[i]) {
                asiento = i;
                break;
            }
        }
        if (asiento < 0) {
            System.out.println("No quedan asientos " + tipo);
            return;
        }

        fila[asiento] = true;

        int descuento = checkDescuento(precioBase, sc);
        Ventas.add(new Venta(tipo, precioBase, descuento, asiento + 1));

        System.out.println("\n-- BOLETA --");
        System.out.println("Se ha comprado una entrada: " + Ventas.get(Ventas.size() - 1).numeroAsiento);
        System.out.println("Con un costo base de: " + Ventas.get(Ventas.size() - 1).valorAsiento);
        System.out.println("Descuento aplicado: " + Ventas.get(Ventas.size() - 1).valorDescuento + "%");
        System.out.println("Costo final de: " + ((Ventas.get(Ventas.size() - 1).valorAsiento) - 
                                                ((Ventas.get(Ventas.size() - 1).valorAsiento) * 
                                                 (Ventas.get(Ventas.size() - 1).valorDescuento)) / 100) );
        System.out.println("Gracias por comprar en el " + nombreTeatro);
        System.out.println("-- FIN DE BOLETA --\n");
    }

    public static int checkDescuento(int valor, Scanner sc) { // Metodo para calcular el descuento
                                                              // Se le pregunta al usuario si es estudiante o adulto mayor, y se aplica el descuento correspondiente
        String mensajeEstudiante = "Esta usted estudiando?\n1.- Si\n2.- No";
        int opcion = leerOpcionValida(sc, 1, 2, mensajeEstudiante);

        if (opcion == 1) {
            return descuentoEstudiante;
        } else {
            String mensajeAdultoMayor = "Es usted un adulto mayor?\n1.- Si\n2.- No";
            opcion = leerOpcionValida(sc, 1, 2, mensajeAdultoMayor);
            if (opcion == 1) {
                return descuentoTerceraEdad;
            }
        }
        // Si no aplica ningún descuento, retorna 0
        return 0;

    }

    public static int leerOpcionValida(Scanner sc, int minimo, int maximo, String mensaje) { // Metodo que lee la opcion del usuario y valida que sea un numero entre el minimo y el maximo
                                                                                             // Tambien maneja excepciones para evitar que el programa se caiga si el usuario ingresa un valor no valido
        int opcion = 0;
        boolean entradaValida = false;
        
        do {
            System.out.println(mensaje);
            
            try {
                opcion = sc.nextInt();
                
                if (opcion >= minimo && opcion <= maximo) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: Por favor ingrese un número entre " + minimo + " y " + maximo);
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor ingrese un número válido");
                sc.nextLine();
            }
        } while (!entradaValida);
        
        return opcion;
    }


}