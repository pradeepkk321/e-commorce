package com.kuriocity.ecom.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "Application is running";
	}
	
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Map> emitEvents(){
		Map map = new HashMap<>();
		return Flux.interval(Duration.ofSeconds(1))
				.map(val -> {
					map.put("value", val);
					return map;
				});
	}

}
