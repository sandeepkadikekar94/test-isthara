package com.upliv.user_service.model;

import java.util.List;
import java.util.Map;

public class TemplateEmail {
    private List<String> toEmail;
    private String templateId;
    private Map<String, String> templateData;

    public List<String> getToEmail() {
        return toEmail;
    }

    public void setToEmail(List<String> toEmail) {
        this.toEmail = toEmail;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<String, String> getTemplateData() {
        return templateData;
    }

    public void setDyanmciData(Map<String, String> templateData) {
        this.templateData = templateData;
    }

    public TemplateEmail(List<String> toEmail, String templateId, Map<String, String> templateData) {
        this.toEmail = toEmail;
        this.templateId = templateId;
        this.templateData = templateData;
    }
}
