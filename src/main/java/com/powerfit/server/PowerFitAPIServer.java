package com.powerfit.server;

// ... (imports iguales a los tuyos)

import com.powerfit.dao.PlanDAO;
import com.powerfit.dao.SocioDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class PowerFitAPIServer {
    private static final int PUERTO = 8080;
    private PlanDAO planDAO;
    private SocioDAO socioDAO;

    public PowerFitAPIServer() {
        this.planDAO = new PlanDAO();
        this.socioDAO = new SocioDAO();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            // ... (mensajes de log iguales)
            while (true) {
                Socket socket = serverSocket.accept();
                // Usamos hilos para no bloquear el servidor con cada cliente
                new Thread(new ManejadorCliente(socket, planDAO, socioDAO)).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PowerFitAPIServer().iniciar();
    }

    private static class ManejadorCliente implements Runnable {
        private Socket socket;
        private PlanDAO planDAO;
        private SocioDAO socioDAO;

        public ManejadorCliente(Socket socket, PlanDAO planDAO, SocioDAO socioDAO) {
            this.socket = socket;
            this.planDAO = planDAO;
            this.socioDAO = socioDAO;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                String linea = reader.readLine();
                if (linea == null || linea.isEmpty()) return;

                String[] partes = linea.split(" ");
                String metodo = partes[0];
                String ruta = partes[1];

                int contentLength = 0;
                String lineaHeaders;
                while ((lineaHeaders = reader.readLine()) != null && !lineaHeaders.isEmpty()) {
                    if (lineaHeaders.toLowerCase().startsWith("content-length:")) {
                        contentLength = Integer.parseInt(lineaHeaders.substring(15).trim());
                    }
                }

                String cuerpo = "";
                if (contentLength > 0) {
                    char[] buffer = new char[contentLength];
                    reader.read(buffer, 0, contentLength);
                    cuerpo = new String(buffer);
                }

                String respuesta = procesarSolicitud(metodo, ruta, cuerpo);
                
                // Respuesta HTTP Estándar
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: application/json; charset=UTF-8");
                writer.println("Access-Control-Allow-Origin: *");
                writer.println("Access-Length: " + respuesta.getBytes("UTF-8").length);
                writer.println();
                writer.println(respuesta);
                writer.flush();

            } catch (Exception e) {
                System.err.println("Error procesando cliente: " + e.getMessage());
            } finally {
                try { if(!socket.isClosed()) socket.close(); } catch (IOException e) {}
            }
        }

        private String procesarSolicitud(String metodo, String ruta, String cuerpo) {
            // ... (Tu lógica de ruteo está correcta)
            return "{\"status\":\"ok\"}"; // Ejemplo simplificado
        }

        /**
         * Extrae valores de un JSON manual.
         * Soporta "clave":"valor" y "clave":valor (números/booleans)
         */
        private String extraerValor(String json, String clave) {
            if (json == null || json.isEmpty()) return null;
            
            // Caso 1: Buscar "clave":"valor" (String)
            String patronString = "\"" + clave + "\":\"";
            int inicio = json.indexOf(patronString);
            
            if (inicio != -1) {
                inicio += patronString.length();
                int fin = json.indexOf("\"", inicio);
                return json.substring(inicio, fin);
            }
            
            // Caso 2: Buscar "clave":valor (Número o Boolean)
            String patronNum = "\"" + clave + "\":";
            inicio = json.indexOf(patronNum);
            if (inicio != -1) {
                inicio += patronNum.length();
                int finComa = json.indexOf(",", inicio);
                int finLlave = json.indexOf("}", inicio);
                
                int fin;
                if (finComa != -1 && finLlave != -1) fin = Math.min(finComa, finLlave);
                else fin = (finComa != -1) ? finComa : finLlave;
                
                return json.substring(inicio, fin).trim().replace("\"", "");
            }
            
            return null;
        }
    }
}