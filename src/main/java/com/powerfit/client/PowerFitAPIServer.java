package com.powerfit.client;



import com.powerfit.dao.PlanDAO;
import com.powerfit.dao.SocioDAO;
import com.powerfit.models.Plan;
import com.powerfit.models.Socio;
import com.powerfit.utils.JsonResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Servidor HTTP REST para PowerFit.
 * Maneja solicitudes HTTP y responde con JSON.
 */
public class PowerFitAPIServer {
    private static final int PUERTO = 8080;
    private final PlanDAO planDAO;
    private final SocioDAO socioDAO;

    public PowerFitAPIServer() {
        this.planDAO = new PlanDAO();
        this.socioDAO = new SocioDAO();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("==============================================");
            System.out.println("PowerFit API Server iniciado en puerto " + PUERTO);
            System.out.println("==============================================");

            while (true) {
                Socket socket = serverSocket.accept();
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
        private final Socket socket;
        private final PlanDAO planDAO;
        private final SocioDAO socioDAO;

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
                writer.println("Content-Length: " + respuesta.getBytes("UTF-8").length);
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
            try {
                if (ruta.startsWith("/api/socios")) {
                    if (metodo.equals("GET")) {
                        List<Socio> socios = socioDAO.obtenerTodos();
                        return JsonResponse.buildResponse(true, "Socios obtenidos", socios);
                    } else if (metodo.equals("POST")) {
                        Socio nuevo = parsearSocio(cuerpo);
                        boolean creado = socioDAO.crear(nuevo);
                        return JsonResponse.buildResponse(creado, creado ? "Socio creado" : "Error", nuevo);
                    }
                } else if (ruta.startsWith("/api/planes")) {
                    List<Plan> planes = planDAO.obtenerTodos();
                    return JsonResponse.buildResponse(true, "Planes obtenidos", planes);
                }
                return JsonResponse.buildResponse(false, "Endpoint no encontrado", null);
            } catch (Exception e) {
                return JsonResponse.buildResponse(false, "Error: " + e.getMessage(), null);
            }
        }

        private Socio parsearSocio(String json) {
            Socio s = new Socio();
            s.setNombreCompleto(extraerValor(json, "nombreCompleto"));
            s.setDni(extraerValor(json, "dni"));
            s.setEmail(extraerValor(json, "email"));
            s.setTelefono(extraerValor(json, "telefono"));
            String idPlan = extraerValor(json, "idPlan");
            if (idPlan != null) s.setIdPlan(Integer.parseInt(idPlan));
            String fecha = extraerValor(json, "fechaVencimiento");
            if (fecha != null) s.setFechaVencimiento(LocalDate.parse(fecha));
            return s;
        }

        private String extraerValor(String json, String clave) {
            String patron = "\"" + clave + "\":\"";
            int inicio = json.indexOf(patron);
            if (inicio == -1) {
                patron = "\"" + clave + "\":";
                inicio = json.indexOf(patron);
                if (inicio == -1) return null;
                inicio += patron.length();
                int fin = json.indexOf(",", inicio);
                if (fin == -1) fin = json.indexOf("}", inicio);
                return json.substring(inicio, fin).trim().replace("\"", "");
            }
            inicio += patron.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        }
    }
}