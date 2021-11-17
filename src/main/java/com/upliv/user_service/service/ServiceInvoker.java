package com.upliv.user_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upliv.user_service.model.SmsModel;
import com.upliv.user_service.model.TemplateEmail;
import com.upliv.user_service.model.common.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceInvoker {

    private final Logger logger = LoggerFactory.getLogger(ServiceInvoker.class);

    @Value("${message.sms.url}")
    private String baseSMSUrl;
    @Value("${message.email.url}")
    private String baseEmailUrl;
    @Value("${message.email.template.url}")
    private String baseTemplateEmailUrl;

    @Autowired
    private RestTemplate restTemplate;

    public void generateSMS(String countryCode, String mobile, String message){
        logger.info("Trying to generate SMS for " + mobile + " message: " + message);
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            SmsModel sms = new SmsModel(countryCode, mobile, message);
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(sms);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseSMSUrl);
            HttpEntity entity = new HttpEntity(body,headers);
            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            logger.info(response.getBody());
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
    //
    public void generateTemplateEmail(String toEmail, String templateId, Map<String, String> templateData){
        logger.info("Trying to generate email for " +toEmail + " template: " + templateId);
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseTemplateEmailUrl);
            List<String> toEmailList = new ArrayList<>();
            toEmailList.add(toEmail);
            TemplateEmail sms = new TemplateEmail(toEmailList, templateId, templateData);
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(sms);
            HttpEntity entity = new HttpEntity(body, headers);
            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            logger.info(response.getBody());
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }


    public void generateEmail(List<String> toEmail,List<String> ccEmail, String subject, String emailBody) {
        logger.info("Trying to generate email for " + toEmail + " subject: " + subject);
        System.out.println("Trying to generate email for " + toEmail + " subject: " + subject);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseEmailUrl);
            EmailRequest emailRequest = new EmailRequest();
            List<String> toEmails = new ArrayList<>();
            toEmails.addAll(toEmail);
//            toEmails.add("aditya@isthara.com");
            emailRequest.setToEmail(toEmails);
            emailRequest.setCcEmail(ccEmail == null ? new ArrayList<>() : ccEmail);
            emailRequest.setSubject(subject);
            emailRequest.setBody(emailBody);

            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(emailRequest);
            HttpEntity entity = new HttpEntity(body, headers);
            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            logger.info(response.getBody());
            System.out.println("Response : " + response.getBody());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
