import java.io.*;
import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);
    private static ProcessBuilder pb = new ProcessBuilder();
    private static Process ps;
    private static String linea;
    private static String os = System.getProperty("os.name");

    public static void main(String[] args) {
        int opc = 0;

        while (opc != 5) {
            System.out.println("");
            System.out.println("*** Menú de opciones ***");
            System.out.println("1. Ejecutar ping.");
            System.out.println("2. Listar archivos.");
            System.out.println("3. Destruir proceso.");
            System.out.println("4. Ejecutar navegador.");
            System.out.println("5. Salir.");
            System.out.print("Opción: ");

            opc = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opc) {
                case 1:
                    opcion1();
                    break;
                case 2:
                    opcion2();
                    break;
                case 3:
                    opcion3();
                    break;
                case 4:
                    opcion4();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void opcion1() {
        try {
            System.out.print("Introduce un destino: ");
            String dst = sc.nextLine();

            if (os.startsWith("Windows")) {
                pb.command("ping", "-n", "3", dst);
            } else {
                pb.command("ping", "-c", "3", dst);
            }
            ps = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void opcion2() {
        try {
            System.out.print("Indica el nombre de archivo de salida: ");
            String archivo = sc.nextLine();

            if (os.startsWith("Windows")) {
                pb.command("cmd", "/c", "dir");
            } else {
                pb.command("ls");
            }
            ps = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo)));

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                pw.println(linea);
            }
            pw.close();
            br.close();

            System.out.println("Listado guardado en " + archivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void opcion3() {
        try {
            if (os.startsWith("Windows")) {
                pb.command("tasklist");
            } else {
                pb.command("ps");
            }
            ps = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();

            System.out.print("Indique el PID del proceso a terminar: ");
            String pid = sc.nextLine();

            if (os.startsWith("Windows")) {
                pb.command("taskkill", "/PID", pid, "/F");
            } else {
                pb.command("kill", pid);
            }
            ps = pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void opcion4() {
        try {
            System.out.print("Introduce la URL (ej: https://www.google.com): ");
            String url = sc.nextLine();

            if (os.startsWith("Windows")) {
                pb.command("cmd", "/c", "start", url);
            } else if (os.startsWith("Linux")) {
                pb.command("xdg-open", url);
            } else {
                pb.command("open", url);
            }

            ps = pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
