package ru.stalser.framework.helpers;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.DefaultAttachmentContent;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;

public class CustomAttachmentRenderer extends FreemarkerAttachmentRenderer {

    public CustomAttachmentRenderer(String templateName) {
        super(templateName);
    }

    @Override
    public DefaultAttachmentContent render(AttachmentData data) {
        return new DefaultAttachmentContent(data.toString(), "text/html", ".html");
    }
}
