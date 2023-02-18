package ru.stalser.framework.helpers;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

public class XMLHelper {

    public static String prettyPrintString(String xmlText) {

        if (Objects.isNull(xmlText)) {
            throw new Error("xmlText аргумент не должен быть null");
        }

        if (xmlText.startsWith("<")) {
            return xmlText;
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutPut = new StreamResult(stringWriter);

            Source xmlInput = new StreamSource(new StringReader(xmlText));
            transformer.transform(xmlInput, xmlOutPut);

            return xmlOutPut.getWriter().toString();
        } catch (TransformerException e) {

            return e.getMessage();
        }
    }
}
