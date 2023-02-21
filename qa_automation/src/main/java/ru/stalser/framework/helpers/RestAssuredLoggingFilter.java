package ru.stalser.framework.helpers;


import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.FilterContext;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestAssuredLoggingFilter extends AllureRestAssured {

    private String requestName = "";
    private String responseName = "";

    public RestAssuredLoggingFilter() {
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        Prettifier prettifier = new Prettifier();
        HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder.create
                        (requestName.isEmpty() ? "API request " + requestSpec.getMethod() + " " + requestSpec.getDerivedPath() : requestName, requestSpec.getURI())
                .setMethod(requestSpec.getMethod())
                .setHeaders(toMapConverter(requestSpec.getHeaders()))
                .setCookies(toMapConverter(requestSpec.getCookies()));
        if (Objects.nonNull(requestSpec.getBody())) {
//            requestAttachmentBuilder.setBody(StringEscapeUtils.escapeHtml4(XMLHelper.prettyPrintString(requestSpec.getBody().toString())));
            requestAttachmentBuilder.setBody(requestSpec.getBody().toString());
        }

        HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        (new CustomAttachmentProcessor()).addAttachment(requestAttachment, new CustomAttachmentRenderer("custom_http_request.ftl"));
        Response response = filterContext.next(requestSpec, responseSpec);
        HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create
                        (responseName.isEmpty() ? "API response" + response.getStatusLine() + " " + requestSpec.getDerivedPath() : responseName)
                .setResponseCode(response.getStatusCode())
                .setHeaders(toMapConverter(response.getHeaders()))
//                .setBody(StringEscapeUtils.escapeHtml4(prettifier.getPrettifiedBodyIfPossible(response, response.getBody())))
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();
        (new CustomAttachmentProcessor()).addAttachment(responseAttachment, new CustomAttachmentRenderer("custom_http_response.ftl"));

        return response;
    }

    private static Map<String, String> toMapConverter(Iterable<? extends NameAndValue> items) {

        Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));

        return result;
    }
}
