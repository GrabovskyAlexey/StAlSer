package ru.stalser.framework.helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.attachment.AttachmentContent;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.AttachmentRenderer;

import java.nio.charset.StandardCharsets;

public class CustomAttachmentProcessor implements AttachmentProcessor<AttachmentData> {
    private final AllureLifecycle lifecycle;

    public CustomAttachmentProcessor() {
        this(Allure.getLifecycle());
    }

    public CustomAttachmentProcessor(AllureLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void addAttachment(AttachmentData attachmentData,
                              AttachmentRenderer<AttachmentData> attachmentRenderer) {

        final AttachmentContent content = attachmentRenderer.render(attachmentData);
        lifecycle.addAttachment(
                attachmentData.getName(),
                content.getContentType(),
                content.getFileExtension(),
                content.getContent().getBytes(StandardCharsets.UTF_8)
        );
    }
}
