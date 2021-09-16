package com.upvote.aismpro.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Random;

public interface EmailServiceInter {

    public MimeMessage createMessage(String to) throws Exception;
    public void sendSimpleMessage(String to) throws Exception;
}
