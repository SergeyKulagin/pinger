package com.kulagin.pinger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class PingerApplication {

	private final DefaultPinger pinger;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PingerApplication.class, args);
		context.close();
	}

	@EventListener(ContextRefreshedEvent.class)
	void onAppStart(ContextRefreshedEvent e) throws Exception{
		pinger.ping();
		log.info("Done.");
	}

}

