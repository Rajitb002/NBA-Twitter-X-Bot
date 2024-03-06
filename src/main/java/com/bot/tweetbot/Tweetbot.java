package com.bot.tweetbot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.redouane59.twitter.ITwitterClientV2;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.io.*;
import java.util.Scanner;

@RestController()
@RequestMapping("bot")
public class Tweetbot {

    private final StatsRepository statsRepository;

    public Tweetbot(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    /**
     * Posts any data to bot
     * @param data: The content that will directly be posted to @NbaBotX
     */
    @PostMapping("miscPost")
   public void mockpost(@RequestBody String data)  {
        ITwitterClientV2 twitterClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(")
                .accessTokenSecret("")
                .apiKey("")
                .apiSecretKey("")
                .build());
        twitterClient.postTweet(data);
   }

    /**
     * Connects to twitter bot and posts nba stats
     */
    @PostMapping("post")
    public void post() throws JsonProcessingException {
        ITwitterClientV2 twitterClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken("")
                .accessTokenSecret("")
                .apiKey("")
                .apiSecretKey("")
                .build());
        String stats = getData();


            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(stats);
            JsonNode dataNode = rootNode.path("data");

            int id = dataNode.path("id").asInt();
            String firstName = dataNode.path("first_name").asText();
            String lastName = dataNode.path("last_name").asText();
            String teamName = dataNode.path("team").path("name").asText();
            String position = dataNode.path("position").asText();


        Stats stats1 = new Stats(id,lastName,firstName,position,teamName);
        System.out.println(stats1.toString());
        twitterClient.postTweet(stats1.toString());
    }

    /**
     * Returns data of NBA Players through get request
     *
     * @return a message about the players
     */
   public String getData() {

       int position = 2;
       try {
           File file = new File("src\\main\\java\\player.txt");
           file.setWritable(true);
           file.setReadable(true);
           Scanner reader = new Scanner(file);
           position =  reader.nextInt();
           reader.close();
           FileWriter writer = new FileWriter(file);
           writer.write(Integer.toString(position + 1));
           writer.close();

       } catch (Exception e){
        System.out.println(e.getMessage());
       }

       //Create a get request to nba api
       StatsController statsController = new StatsController(statsRepository);
       RestClient restClient = RestClient.create();
       return  restClient.get()
               .uri("https://api.balldontlie.io/v1/players/{position}", position)
               .accept(MediaType.APPLICATION_JSON)
               .header("Authorization", "")
               .retrieve()
               .body(String.class);
   }

}
