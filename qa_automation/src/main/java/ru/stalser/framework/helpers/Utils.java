package ru.stalser.framework.helpers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public static <T> String convertListMapToHorizontalHTML(List<Map<String, T>> maps) {

        if (Objects.isNull(maps) || maps.isEmpty()) {

            return String.valueOf(maps);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<style>table { border-collapse: collapse;} table, th, td { border:1px black solid }</style>");
        sb.append("<table>");
        sb.append("<thead>").append("<th>").append("##").append("</th>");
        for (String key : maps.get(0).keySet()) {
            sb.append("<th>").append(key).append("</th>");
        }
        sb.append("</thead>");
        for (int i = 0; i < maps.size(); i++) {
            sb.append("<tr>").append("<td>").append(i + 1).append("/<td>");
            for (String key : maps.get(0).keySet()) {
                sb.append("<td>").append(maps.get(i).get(key)).append("/<td>");
            }
            sb.append("/<tr>");
        }
        sb.append("/<table>");

        return sb.toString();
    }
}
