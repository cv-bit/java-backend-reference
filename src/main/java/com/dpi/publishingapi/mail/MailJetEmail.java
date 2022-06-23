package com.dpi.publishingapi.mail;

import org.json.JSONObject;

public class MailJetEmail {
    private String to;
    private String subject;
    private int templateId;
    private JSONObject templateVariables;

    public MailJetEmail(String to, String subject, int templateId, JSONObject templateVariables) {
        this.to = to;
        this.subject = subject;
        this.templateId = templateId;
        this.templateVariables = templateVariables;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public JSONObject getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(JSONObject templateVariables) {
        this.templateVariables = templateVariables;
    }
}
