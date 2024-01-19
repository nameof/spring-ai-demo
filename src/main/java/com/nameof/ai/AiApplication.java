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
		// 根据需要，设置代理
		SpringApplication.run(AiApplication.class, args);
	}

	@GetMapping
	public String chat(@RequestParam(value = "message", defaultValue = "hello") String msg) {
		return aiClient.generate(msg);
	}
}
