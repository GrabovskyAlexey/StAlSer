package ru.stalser.framework.helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import netscape.javascript.JSObject;
import org.junit.platform.commons.util.ExceptionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ru.stalser.framework.helpers.SoftAssertHelper.softly;

public class AllureHelper {

//    public static void attachJson(String title, JSONObject json) {
//
//    }

    public static void err(String message) {

        if (Objects.nonNull(message)) {
            String uuid = UUID.randomUUID().toString();
            StepResult step = new StepResult()
                    .setName(message)
                    .setStatus(Status.FAILED);
            Allure.getLifecycle().startStep(uuid, step);
            StepsHierarchy.get().onFailStart(message);
            StepsHierarchy.get().colourStepRed();
            StepsHierarchy.get().onStepStop();
            Allure.getLifecycle().stopStep(uuid);
        }
    }

    public static void err(Throwable throwable) {

        String message = Objects.isNull(throwable.getMessage())
                ? "Exception без текста"
                : throwable.getMessage();
            String uuid = UUID.randomUUID().toString();
            StepResult step = new StepResult()
                    .setName(message)
                    .setStatus(Status.FAILED);
            Allure.getLifecycle().startStep(uuid, step);
            StepsHierarchy.get().onFailStart(message);
            attachTxt("Стектрейс ошибки", ExceptionUtils.readStackTrace(throwable));
            softly().collectAssertionError(new AssertionError(
                    (throwable instanceof Exception ? "Ошибка автотеста: " : "") + message
            ));
            StepsHierarchy.get().colourStepRed();
            StepsHierarchy.get().onStepStop();
            Allure.getLifecycle().stopStep(uuid);

    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String attachTxt(String title, String message) {
        return message;
    }

    public static <T> void attachMapAsTable(String title, Map<String, T> map) {

        attachHtml(title, convertMapToHtml(map));
    }

    static void attachHtml(String title, String html) {

        html = html
                .replace("<br>\r\n", "\n")
                .replace("</br>\r\n", "\n")
                .replace("<br>\r\n", "\n")
                .replace("</br>\r\n", "\n")
                .replace("\r\n", "\n")
                .replace("\n", "</br>\n");
        Allure.addAttachment(title, "text/html", html, ".html");
    }

    private static <T> String convertMapToHtml(Map<String, T> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>table { border-collapse;} table, th, td { border:1px black solid }</style>");
        sb.append("<table>");
        sb.append("<thead>").append("<th>").append("Ключ").append("</th>").append("<th>").append("Значение").append("</th>").append("</thead>");
        map.keySet().stream().sorted().forEach(key ->
                sb.append("<tr>").append("<td>").append(key).append("</td>").append("<td>")
                        .append(map.get(key) == null ? "null" : map.get(key).toString())
                        .append("</td>").append("</tr>")
        );
        sb.append("/<table>");

        return sb.toString();
    }
}
