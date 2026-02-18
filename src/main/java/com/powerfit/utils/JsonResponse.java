package com.powerfit.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Clase para construir respuestas JSON manualmente
 */
public class JsonResponse {

    public static String toJson(Object data) {
        if (data == null) return "null";
        if (data instanceof String) return "\"" + data + "\"";
        if (data instanceof Number || data instanceof Boolean) return data.toString();
        if (data instanceof Map) return mapToJson((Map<?, ?>) data);
        if (data instanceof List) return listToJson((List<?>) data);
        return objectToJson(data);
    }

    public static String mapToJson(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) sb.append(",");
            sb.append("\"").append(entry.getKey()).append("\":").append(toJson(entry.getValue()));
            first = false;
        }
        return sb.append("}").toString();
    }

    public static String listToJson(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJson(list.get(i)));
        }
        return sb.append("]").toString();
    }

    public static String objectToJson(Object obj) {
        if (obj instanceof com.powerfit.models.Plan) {
            com.powerfit.models.Plan plan = (com.powerfit.models.Plan) obj;
            Map<String, Object> map = new HashMap<>();
            map.put("idPlan", plan.getIdPlan());
            map.put("nombrePlan", plan.getNombrePlan());
            map.put("precio", plan.getPrecio());
            return mapToJson(map);
        } else if (obj instanceof com.powerfit.models.Socio) {
            com.powerfit.models.Socio socio = (com.powerfit.models.Socio) obj;
            Map<String, Object> map = new HashMap<>();
            map.put("idSocio", socio.getIdSocio());
            map.put("nombreCompleto", socio.getNombreCompleto());
            map.put("estado", socio.getEstado());
            return mapToJson(map);
        }
        return "null";
    }

    public static String buildResponse(boolean success, String mensaje, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("mensaje", mensaje);
        response.put("data", data);
        return mapToJson(response);
    }
}