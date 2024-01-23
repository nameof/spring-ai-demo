package com.nameof.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.client.AiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@SpringBootApplication
@RequestMapping("/chat")
@RestController
public class AiApplication {
	private final AiClient aiClient;

	public static void main(String[] args) {
		System.setProperty("http.proxyHost","127.0.0.1");
		System.setProperty("http.proxyPort","7890");
		System.setProperty("https.proxyHost","127.0.0.1");
		System.setProperty("https.proxyPort","7890");
		SpringApplication.run(AiApplication.class, args);
	}

	@GetMapping
	public String chat(@RequestParam(value = "message", defaultValue = "hello") String msg) {
		return aiClient.generate(msg);
	}
}
