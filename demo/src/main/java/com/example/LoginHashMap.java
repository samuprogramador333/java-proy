package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginHashMap {

    // Base de datos de usuarios y horarios
    private static Map<String, String> baseDeDatos = new HashMap<>();
    private static Map<String, Map<String, Map<String, String>>> horarios = new HashMap<>();

    private static String tipoUsuario = "";

    private static final String usuarioJefe = "admin";
    private static final String contrasenaJefe = "clave123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        // Menú inicial para seleccionar entre Empleado o Jefe
        seleccionarTipoDeUsuario(scanner);

        if (tipoUsuario.equals("Jefe")) {
            iniciarSesionJefe(scanner);
        } else {
            while (opcion != 4) {
                try {
                    System.out.println("\n--- Menú Principal ---");
                    System.out.println("1. Registrarte");
                    System.out.println("2. Iniciar sesión");
                    System.out.println("3. Registrar horarios");
                    System.out.println("4. Salir");
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
                            break;
                        default:
                            System.out.println("Error, intente nuevamente.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingrese un número válido.");
                }
            }
        }

        scanner.close();
    }

    // Seleccionar tipo de usuario (Empleado o Jefe)
    private static void seleccionarTipoDeUsuario(Scanner scanner) {
        System.out.println("\nSeleccione su tipo de usuario:");
        System.out.println("1. Empleado");
        System.out.println("");
        System.out.println("2. Jefe");
        System.out.print("Seleccione una opción (1/2): ");
        int opcionTipo = Integer.parseInt(scanner.nextLine());

        if (opcionTipo == 1) {
            tipoUsuario = "Empleado";
            System.out.println("Ha seleccionado: Empleado");
            System.out.println("");
        } else if (opcionTipo == 2) {
            tipoUsuario = "Jefe";
            System.out.println("");
            System.out.println("Ha seleccionado: Jefe");
        } else {
            System.out.println("Opción no válida. Se seleccionará por defecto: Empleado.");
            tipoUsuario = "Empleado";
        }
    }

    // Iniciar sesión del Jefe con usuario y contraseña
    private static void iniciarSesionJefe(Scanner scanner) {
        System.out.println("\n--- Ingreso especial para Jefe ---");
        System.out.print("Ingrese el nombre de usuario Jefe: ");
        System.out.println("");
        String usuario = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese la contraseña Jefe: ");
        String contrasena = scanner.nextLine();

        if (usuario.equals(usuarioJefe) && contrasena.equals(contrasenaJefe)) {
            System.out.println("-----------------------------------");
            System.out.println("¡Inicio de sesión exitoso Jefe!");
            mostrarMenuJefe(scanner);
        } else {
            System.out.println("Usuario o contraseña incorrectos para el Jefe.");
        }
    }

    // Menú para el Jefe
    private static void mostrarMenuJefe(Scanner scanner) {
        int opcion = -1;

        while (opcion != 5) {
            System.out.println("\n--- Menú de Jefe ---");
            System.out.println("1. Eliminar horario de entrada o salida");
            System.out.println("2. Eliminar algún usuario registrado");
            System.out.println("3. Agregar o modificar horarios");
            System.out.println("4. Mostrar todos los empleados y sus horarios");
            System.out.println("");
            System.out.println("5. Salir");
            System.out.println("");
            System.out.print("Seleccionar una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    eliminarHorario(scanner);
                    break;
                case 2:
                    eliminarUsuario(scanner);
                    break;
                case 3:
                    agregarOmodificarHorario(scanner);
                    break;
                case 4:
                    mostrarEmpleadosYHorarios();
                    break;
                case 5:
                    System.out.println("Saliendo del menú del Jefe.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Opción 1: Eliminar horario de entrada o salida
    private static void eliminarHorario(Scanner scanner) {
        System.out.print("Ingrese el nombre del empleado: ");
        System.out.println("");
        String nombre = scanner.nextLine();

        if (!horarios.containsKey(nombre)) {
            System.out.println("El usuario no tiene horarios registrados.");
            return;
        }

        System.out.print("Ingrese la etiqueta del horario que desea eliminar (entrada o salida): ");
        String etiqueta = scanner.nextLine();

        if (horarios.get(nombre).containsKey(etiqueta)) {
            horarios.get(nombre).remove(etiqueta);
            System.out.println("-----------------------------------");
            System.out.println("Horario eliminado exitosamente.");
        } else {
            System.out.println("Horario no encontrado.");
        }
    }

    // Opción 2: Eliminar usuario
    private static void eliminarUsuario(Scanner scanner) {
        System.out.print("Ingrese el nombre de usuario a eliminar: ");
        String nombre = scanner.nextLine();

        if (baseDeDatos.containsKey(nombre)) {
            baseDeDatos.remove(nombre);
            horarios.remove(nombre);  // Eliminar también los horarios del usuario
            System.out.println("-----------------------------------");
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    // Opción 3: Agregar o modificar horario
    private static void agregarOmodificarHorario(Scanner scanner) {
        System.out.print("Ingrese el nombre del empleado: ");
        System.out.println("");
        String nombre = scanner.nextLine();

        if (!baseDeDatos.containsKey(nombre)) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.print("Ingrese la etiqueta del horario (entrada o salida): ");
        System.out.println("");
        String tipoHorario = scanner.nextLine().toLowerCase();

        if (!tipoHorario.equals("entrada") && !tipoHorario.equals("salida")) {
            System.out.println("Opción no válida. Debe ingresar 'entrada' o 'salida'.");
            return;
        }

        // Ingreso de la fecha y horario
        System.out.print("Ingrese la fecha (formato AÑO-MES-DIA): ");
        System.out.println("");
        String fecha = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese el día de la semana: ");
        String dia = scanner.nextLine();
        System.out.println("");
        System.out.print("Ingrese el horario (formato HH:mm): ");
        String horario = scanner.nextLine();

        // Guardar detalles del horario
        Map<String, String> detalleHorario = new HashMap<>();
        detalleHorario.put("Fecha", fecha);
        detalleHorario.put("Dia", dia);
        detalleHorario.put("Hora", horario);

        // Registrar horario
        horarios.putIfAbsent(nombre, new HashMap<>());
        horarios.get(nombre).put(tipoHorario, detalleHorario);
        System.out.println("-----------------------------------");
        System.out.println("Horario de " + tipoHorario + " registrado o modificado exitosamente.");
    }

    // Opción 4: Mostrar todos los empleados y sus horarios
    private static void mostrarEmpleadosYHorarios() {
        if (baseDeDatos.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\n--- Lista de Empleados con sus Horarios ---");
        for (String nombre : baseDeDatos.keySet()) {
            System.out.println("Empleado: " + nombre);
            if (horarios.containsKey(nombre) && !horarios.get(nombre).isEmpty()) {
                System.out.println("  Horarios registrados:");
                for (String etiqueta : horarios.get(nombre).keySet()) {
                    Map<String, String> horario = horarios.get(nombre).get(etiqueta);
                    System.out.println("    Tipo de horario: " + etiqueta);
                    System.out.println("      Fecha: " + horario.get("Fecha"));
                    System.out.println("      Día: " + horario.get("Dia"));
                    System.out.println("      Hora: " + horario.get("Hora"));
                }
            } else {
                System.out.println("  No hay horarios registrados.");
            }
        }
    }

    // Registrar un usuario
    private static void registrarUsuario(Scanner scanner) {
        try {
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
                horarios.put(nombre, new HashMap<>()); // Inicializar mapa de horarios para el nuevo usuario
                System.out.println("-----------------------------------");
                System.out.println("Usuario registrado exitosamente.");
            }

        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Iniciar sesión de un usuario
    private static void iniciarSesion(Scanner scanner) {
        try {
            System.out.print("Ingrese su usuario: ");
            System.out.println("");
            String nombre = scanner.nextLine();
            System.out.println("");
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();

            if (baseDeDatos.containsKey(nombre) && baseDeDatos.get(nombre).equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso.");
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
            }

        } catch (Exception e) {
            System.out.println("Error durante el inicio de sesión: " + e.getMessage());
        }
    }

    // Registrar un horario de entrada o salida
    private static void registrarHorario(Scanner scanner) {
        try {
            System.out.print("Ingrese su usuario: ");
            System.out.println("");
            String nombre = scanner.nextLine();

            if (!baseDeDatos.containsKey(nombre)) {
                System.out.println("Usuario no encontrado. Inicia sesión primero.");
                return;
            }

            System.out.print("Ingrese si el horario es de 'entrada' o 'salida': ");
            String tipoHorario = scanner.nextLine().toLowerCase();

            if (!tipoHorario.equals("entrada") && !tipoHorario.equals("salida")) {
                System.out.println("Opción no válida. Debe ingresar 'entrada' o 'salida'.");
                return;
            }

            // Ingreso de la fecha y horario
            System.out.print("Ingrese la fecha (formato AÑO-MES-DIA): ");
            String fecha = scanner.nextLine();
            System.out.print("Ingrese el día de la semana: ");
            String dia = scanner.nextLine();
            System.out.print("Ingrese el horario (formato HH:mm): ");
            String horario = scanner.nextLine();

            Map<String, String> detalleHorario = new HashMap<>();
            detalleHorario.put("Fecha", fecha);
            detalleHorario.put("Dia", dia);
            detalleHorario.put("Hora", horario);

            horarios.get(nombre).put(tipoHorario, detalleHorario);
            System.out.println("Horario de " + tipoHorario + " registrado exitosamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar el horario: " + e.getMessage());
        }
    }
}
