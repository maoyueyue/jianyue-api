package com.soft1721.jianyue.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class TaskService {
    @Autowired
    private MailService mailService;
    
    @Scheduled(cron = "0 4 10 15 4 ?")
    public void proces(){
        mailService.sendMail("363645822@qq.com","张文旭发来的邮件","111111");
    }
  }