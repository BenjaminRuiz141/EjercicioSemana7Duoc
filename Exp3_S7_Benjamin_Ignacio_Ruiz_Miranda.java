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
    int totalVentas;

    public Venta(String tipoAsiento, int valorAsiento, int valorDescuento, int numeroAsiento) {
        this.tipoAsiento = tipoAsiento;
        this.valorAsiento = valorAsiento;
        this.valorDescuento = valorDescuento;
        this.numeroAsiento = numeroAsiento;
    }

}

public class Exp3_S7_Benjamin_Ignacio_Ruiz_Miranda {

    // Variables de uso publico
    static String nombreTeatro = "Teatro Moro";
    static boolean[] filaASientosVIP = new boolean[10];
    static boolean[] filaASientosPlatea = new boolean[10];
    static boolean[] filaASientosBalcon = new boolean[10];
    static int descuentoEstudiante = 10;
    static int descuentoTerceraEdad = 15;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Venta> Ventas = new ArrayList<>();
        boolean salir = false;

        do {

            System.out.println("Bienvenido al sistema de ventas del " + nombreTeatro);
            System.out.println("Escriba la opcion del menu que quiera realizar:");
            System.out.println("1 - Venta de entradas");
            System.out.println("2 - Resumen de ventas");
            System.out.println("3 - Calcular Ingresos Totales");
            System.out.println("4 - Salir del programa");

            int opcion = sc.nextInt();

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

    public static int checkAsientosDisponiblesVIP() {
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

    public static void sistemaResumen(ArrayList<Venta> Ventas) {

        if (Ventas.isEmpty()) {
            System.out.println("No hay asientos comprados.");
            return;
        }
        
        for (int i = 0; i < Ventas.size(); i++) {
            System.out.println("\n-- COMPRA #" + (i+1) + " --");
            System.out.println("Entrada: " + Ventas.get(i).numeroAsiento);
            System.out.println("Costo base: " + Ventas.get(i).valorAsiento);
            System.out.println("Descuento aplicado: " + Ventas.get(i).valorDescuento + "%");
            System.out.println("Costo final: " + ((Ventas.get(i).valorAsiento) - 
                                                  ((Ventas.get(i).valorAsiento) * 
                                                   (Ventas.get(i).valorDescuento)) / 100));
            System.out.println("");
        }
    }

    public static void sistemaIngresosTotales(ArrayList<Venta> Ventas) {
        int totalIngresos = 0;

        for (int i = 0; i < Ventas.size(); i++) {
            totalIngresos += ((Ventas.get(i).valorAsiento) - 
                            ((Ventas.get(i).valorAsiento) * 
                            (Ventas.get(i).valorDescuento)) / 100);
        }

        System.out.println("El total de ingresos generado es de: " + totalIngresos);
    }

    public static void sistemaVentas (ArrayList<Venta> Ventas, Scanner sc) {
        System.out.println("-- Usted esta comprando una entrada --");
        System.out.println("Asientos VIP Disponibles: " + checkAsientosDisponiblesVIP());
        System.out.println("Asientos Platea Disponibles: " + checkAsientosDisponiblesPlatea());
        System.out.println("Asientos Balcon Disponibles: " + checkAsientosDisponiblesBalcon());
        System.out.println();
        
        System.out.println("Seleccione que tipo de asiento desea comprar");
        System.out.println("1.- Asiento VIP");
        System.out.println("2.- Asiento Platea");
        System.out.println("3.- Asiento Balcon");
        int opcion;
        opcion = sc.nextInt();

        switch (opcion) {
            case 1 -> reservarAsiento(Ventas, sc,
                                     filaASientosVIP, "VIP", 13500);
            case 2 -> reservarAsiento(Ventas, sc,
                                     filaASientosPlatea, "Platea", 9500);
            case 3 -> reservarAsiento(Ventas, sc,
                                     filaASientosBalcon, "Balcón", 7000);
        }
    }

    public static void reservarAsiento(ArrayList<Venta> Ventas, Scanner sc, boolean[] fila, String tipo, int precioBase) {
        // Buscar el primer índice libre en la fila de asientos.
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
    






    public static int checkDescuento(int valor, Scanner sc) {
        int opcion;
        System.out.println("Esta usted estudiando?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        opcion = sc.nextInt();

        if (opcion == 1) {
            return descuentoEstudiante;
        } else {
            System.out.println("¿Es usted persona de la tercera edad?");
            System.out.println("1.- Sí");
            System.out.println("2.- No");
            opcion = sc.nextInt();
            if (opcion == 1) {
                return descuentoTerceraEdad;
            }
        }
        // Si no aplica ningún descuento, retorna 0
        return 0;

    }

}