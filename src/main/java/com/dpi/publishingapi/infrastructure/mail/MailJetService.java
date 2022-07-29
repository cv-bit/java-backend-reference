package com.dpi.publishingapi.infrastructure.mail;

import com.dpi.publishingapi.infrastructure.exceptions.CustomException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MailJetService {
    private final MailjetClient client;

    @Value("${mail.fromAddress}")
    private String fromAddress;

    @Autowired
    public MailJetService(MailjetClient client) {
        this.client = client;
    }

    private JSONObject makeDefaultJSONObject(MailJetEmail email) {
        return new JSONObject()
                .put(Emailv31.Message.FROM,
                        new JSONObject()
                                .put("Email", fromAddress)
                                .put("Name", "Digital Publishing Inc"))
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", email.getTo())))
                .put(Emailv31.Message.TEMPLATEID, email.getTemplateId())
                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                .put(Emailv31.Message.SUBJECT,
                        email.getSubject())
                .put(Emailv31.Message.VARIABLES, email.getTemplateVariables());
    }

    // send json object as email
    private void makeDefaultRequest(JSONObject jsonObject) {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(jsonObject));

        try {
            client.post(request);
        } catch (MailjetException e) {
            throw new CustomException("Mailjet failed to send the email with the following error: " + e.getMessage(),
                    HttpStatus.BAD_GATEWAY);
        }
    }

    public void sendVerificationEmail(String to, Long confirmationToken) {
        JSONObject templateVariables = new JSONObject()
                .put("FOUR_DIGIT_CODE",
                        confirmationToken)
                .put("TEAM_NAME", "Digital Publishing Inc")
                .put("IMAGE_LINK",
                        "https://dpi-assets.s3.us-east-2.amazonaws.com/misc/DPI_logo.svg")
                .put("DOMAIN_NAME",
                        "Digital Publishing Inc");

        MailJetEmail email = new MailJetEmail(to, "Account Creation Confirmation", 4028975, templateVariables);
        JSONObject fullEmailJson = makeDefaultJSONObject(email);

        makeDefaultRequest(fullEmailJson);
    }
}
