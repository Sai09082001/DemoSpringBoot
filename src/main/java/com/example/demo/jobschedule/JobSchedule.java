
package com.example.demo.jobschedule;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.jobschedule.JobSchedule;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {
	
	@Autowired //DI : dependency inject
	UserRepo userRepo;
	
	@Autowired //DI : dependency inject
	EmailService emailService;
    @Scheduled(fixedDelay = 60000)
	// GIAY - PHUT - GIO - NGAY - THANG - THU
   // @Scheduled(cron = "0 * * * * *")
    public void hello() {
    	log.info("Hello");
    	emailService.testMail();
    }
    
    @Scheduled(cron = "0 09 23 * * *")
    public void morning() {
    	Calendar cal = Calendar.getInstance();
    	int date = cal.get(Calendar.DATE);
    	// thang 1 tuong ung 0
    	int month = cal.get(Calendar.MONTH) + 1;
    	List<User> users = userRepo.searchByBirthday(date, month);
    	for (User u : users) {
    		log.info("Happy birthday "+u.getName());
    		emailService.sendBirthdayEmail(u.getEmail(),u.getName());
    	}
    	log.info("Good Morning");
    }
}