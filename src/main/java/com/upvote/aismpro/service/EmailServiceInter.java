package com.upvote.aismpro.service;

import javax.mail.internet.MimeMessage;

public interface EmailServiceInter {

    public MimeMessage createMessage(String to) throws Exception;
    public void sendSimpleMessage(String to) throws Exception;
}
