package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;

public class LoginHashMap {

    // Base de datos de usuarios y horarios
    private static Map<String, String> baseDeDatos = new HashMap<>();
    private static Map<String, Map<String, Map<String, String>>> horarios = new HashMap<>();
    private static Map<String, LocalDateTime> inicioSesion = new HashMap<>(); // Para el registro de inicio de sesión

    private static String tipoUsuario = "";

    private static final String usuarioJefe = "admin";
    private static final String contrasenaJefe = "clave123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menú inicial para seleccionar entre Empleado o Jefe
        while (true) {
            seleccionarTipoDeUsuario(scanner);

            if (tipoUsuario.equals("Jefe")) {
                iniciarSesionJefe(scanner);
            } else {
                mostrarMenuEmpleado(scanner);
            }
        }
    }

    private static void seleccionarTipoDeUsuario(Scanner scanner) {
        System.out.println("\nSeleccione su tipo de usuario:");
        System.out.println("1. Empleado");
        System.out.println("");
        System.out.println("2. Jefe");
        System.out.println("");
        System.out.print("Seleccione una opción (1/2): ");
        int opcionTipo = Integer.parseInt(scanner.nextLine());

        if (opcionTipo == 1) {
            tipoUsuario = "Empleado";
            System.out.println("Ha seleccionado: Empleado\n");
        } else if (opcionTipo == 2) {
            tipoUsuario = "Jefe";
            System.out.println("Ha seleccionado: Jefe\n");
        } else {
            System.out.println("Opción no válida. Se seleccionará por defecto: Empleado.");
            tipoUsuario = "Empleado";
        }
    }

    // Iniciar sesión del Jefe con usuario y contraseña
    private static void iniciarSesionJefe(Scanner scanner) {
        System.out.println("\n--- Ingreso especial para Jefe ---");
        System.out.println("---------------------");
        System.out.print("Ingrese el nombre de usuario Jefe: ");
        String usuario = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese la contraseña Jefe: ");
        String contrasena = scanner.nextLine();

        if (usuario.equals(usuarioJefe) && contrasena.equals(contrasenaJefe)) {
            System.out.println("");
            System.out.println("¡Inicio de sesión exitoso Jefe!");
            mostrarMenuJefe(scanner);
        } else {
            System.out.println("Usuario o contraseña incorrectos para el Jefe.");
        }
    }

    // Menú para el Jefe
    private static void mostrarMenuJefe(Scanner scanner) {
        int opcion = -1;

        while (opcion != 3) {
            System.out.println("\n--- Menú de Jefe ---");
            System.out.println("1. Ver registros de inicios de sesión o horarios");
            System.out.println("");
            System.out.println("2. Eliminar inicio de sesión o registro de horario");
            System.out.println("");
            System.out.println("3. Salir");
            System.out.println("-----------------------------------");
            System.out.print("Seleccionar una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    verRegistrosDeHorariosEIniciosDeSesion();
                    break;
                case 2:
                    eliminarRegistro(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del menú del Jefe.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Menú para el Empleado
    private static void mostrarMenuEmpleado(Scanner scanner) {
        int opcion = -1;

        while (opcion != 4) {
            try {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("");
                System.out.println("1. Registrarte");
                System.out.println("");
                System.out.println("2. Iniciar sesión");
                System.out.println("");
                System.out.println("3. Registrar horarios");
               System.out.println("");
                System.out.println("4. Salir");
                System.out.println("-----------------------------------");
                System.out.println("");
                System.out.print("Seleccionar una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarUsuario(scanner);
                        break;
                    case 2:
                        iniciarSesion(scanner);
                        break;
                    case 3:
                        registrarHorario(scanner);
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        return; // para volver al menu inicial
                    default:
                        System.out.println("Error, intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    // Registrar un usuario
    private static void registrarUsuario(Scanner scanner) {
        System.out.print("Ingrese el nombre de usuario: ");
        System.out.println("");
        String nombre = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        if (baseDeDatos.containsKey(nombre)) {
            System.out.println("Usuario ya existe. Intente otro nombre.");
        } else {
            baseDeDatos.put(nombre, contrasena);
            horarios.put(nombre, new HashMap<>());
            System.out.println("Usuario registrado exitosamente.");
        }
    }

    // Iniciar sesión de un usuario
    private static void iniciarSesion(Scanner scanner) {
        System.out.print("Ingrese su usuario: ");
        System.out.println("");
        String nombre = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (baseDeDatos.containsKey(nombre) && baseDeDatos.get(nombre).equals(contrasena)) {
            inicioSesion.put(nombre, LocalDateTime.now());
            System.out.println("Inicio de sesión exitoso.");
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    // Registrar un horario de entrada o salida
    private static void registrarHorario(Scanner scanner) {
        System.out.println("-----------------------");
        System.out.print("Ingrese su usuario: ");
        String nombre = scanner.nextLine();

        if (!baseDeDatos.containsKey(nombre)) {
            System.out.println("Usuario no encontrado. Inicia sesión primero.");
            return;
        }
        System.out.println("");
        System.out.print("Ingrese si el horario es de 'entrada' o 'salida': ");
        String tipoHorario = scanner.nextLine().toLowerCase();

        if (!tipoHorario.equals("entrada") && !tipoHorario.equals("salida")) {
            System.out.println("Opción no válida. Debe ingresar 'entrada' o 'salida'.");
            return;
        }

        // Selección del día de la semana
        System.out.println("Seleccione el día de la semana:");
        System.out.println("-----------------------");
        System.out.println("1. Lunes");
        System.out.println("");
        System.out.println("2. Martes");
        System.out.println("");
        System.out.println("3. Miércoles");
        System.out.println("");
        System.out.println("4. Jueves");
        System.out.println("");
        System.out.println("5. Viernes");
        System.out.println("");
        System.out.println("6. Sábado");
        System.out.println("");
        System.out.print("Seleccione una opción: ");
        int opcionDia = Integer.parseInt(scanner.nextLine());

        String dia = "";
        switch (opcionDia) {
            case 1:
                dia = "Lunes";
                break;
            case 2:
                dia = "Martes";
                break;
            case 3:
                dia = "Miércoles";
                break;
            case 4:
                dia = "Jueves";
                break;
            case 5:
                dia = "Viernes";
                break;
            case 6:
                dia = "Sábado";
                break;
            default:
                System.out.println("Opción no válida. Usando 'Lunes' por defecto.");
                dia = "Lunes";
                break;
        }

        // Ingreso de la hora
        System.out.print("Ingrese la hora (formato HH:mm): ");
        String hora = scanner.nextLine();

        // para almacenar el horario
        Map<String, String> detalleHorario = new HashMap<>();
        detalleHorario.put("Dia", dia);
        detalleHorario.put("Hora", hora);

        horarios.get(nombre).put(tipoHorario, detalleHorario);
        System.out.println("Horario de " + tipoHorario + " registrado exitosamente.");
    }

    // Ver registros de horarios o inicios de sesión
    private static void verRegistrosDeHorariosEIniciosDeSesion() {
        if (baseDeDatos.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\n--- Registros de Horarios e Inicios de Sesión ---");
        for (String nombre : baseDeDatos.keySet()) {
            System.out.println("Empleado: " + nombre);
            // Ver hora de inicio de sesión
            LocalDateTime inicio = inicioSesion.get(nombre);
            System.out.println("  Hora de inicio de sesión: " + (inicio != null ? inicio : "No ha iniciado sesión"));

            // Ver los horarios de entrada y salida
            if (horarios.containsKey(nombre) && !horarios.get(nombre).isEmpty()) {
                System.out.println("  Horarios registrados:");
                for (String etiqueta : horarios.get(nombre).keySet()) {
                    Map<String, String> horario = horarios.get(nombre).get(etiqueta);
                    System.out.println("    Tipo de horario: " + etiqueta);
                    System.out.println("      Día: " + horario.get("Dia"));
                    System.out.println("      Hora: " + horario.get("Hora"));
                }
            } else {
                System.out.println("  No hay horarios registrados.");
            }
        }
    }

    // Eliminar un registro de inicio de sesión o horario
    private static void eliminarRegistro(Scanner scanner) {
        System.out.println("Seleccione el tipo de registro a eliminar:");
        System.out.println("");
        System.out.println("1. Eliminar un inicio de sesión");
        System.out.println("");
        System.out.println("2. Eliminar un horario de entrada o salida");
        System.out.println("");
        System.out.print("Seleccionar una opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                eliminarInicioSesion(scanner);
                break;
            case 2:
                eliminarHorario(scanner);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    // Eliminar un inicio de sesión
    private static void eliminarInicioSesion(Scanner scanner) {
        System.out.println("---------------");
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();

        if (inicioSesion.containsKey(nombre)) {
            inicioSesion.remove(nombre);
            System.out.println("Inicio de sesión eliminado exitosamente.");
        } else {
            System.out.println("No se encontró inicio de sesión para el empleado.");
        }
    }

    // Eliminar un horario de entrada o salida
    private static void eliminarHorario(Scanner scanner) {
        System.out.println("---------------------------");
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();

        if (!horarios.containsKey(nombre)) {
            System.out.println("El usuario no tiene horarios registrados.");
            return;
        }

        System.out.print("Ingrese la etiqueta del horario que desea eliminar (entrada o salida): ");
        String etiqueta = scanner.nextLine();

        if (horarios.get(nombre).containsKey(etiqueta)) {
            horarios.get(nombre).remove(etiqueta);
            System.out.println("Horario eliminado exitosamente.");
        } else {
            System.out.println("Horario no encontrado.");
           
        }
    }
}