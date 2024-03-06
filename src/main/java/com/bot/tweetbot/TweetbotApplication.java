package com.bot.tweetbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/playerStats")
@SpringBootApplication
public class TweetbotApplication {

    public static void main(String[] args) {
		SpringApplication.run(TweetbotApplication.class, args);
	}

}
