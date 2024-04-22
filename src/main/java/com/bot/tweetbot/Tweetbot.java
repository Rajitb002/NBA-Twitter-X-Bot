package com.bot.tweetbot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import io.github.redouane59.twitter.ITwitterClientV2;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@RestController()
@RequestMapping("bot")
public class Tweetbot {

    String[] players;

    private final StatsRepository statsRepository;
    private final CountRepository countRepository;

    public Tweetbot(StatsRepository statsRepository, CountRepository countRepository) {
        this.statsRepository = statsRepository;
        this.countRepository = countRepository;
    }

    /**
     * Posts any data to bot
     * @param data: The content that will directly be posted to @NbaBotX
     */
    @PostMapping("miscPost")
   public void miscPost(@RequestBody String data)  {
        ITwitterClientV2 twitterClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken("")
                .accessTokenSecret("")
                .apiKey("")
                .apiSecretKey("")
                .build());
        twitterClient.postTweet(data);
   }

    /**
     * Connects to Twitter bot and posts nba stats
     */
    @PostMapping("post")
    public void post(@RequestBody int token) throws JsonProcessingException {

        ITwitterClientV2 twitterClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken("")
                .accessTokenSecret("")
                .apiKey("")
                .apiSecretKey("")
                .build());
        //Contains literals of MVP

        /*The usual daily tweet*/
        if(token == 1) {
            String stats = getData();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(stats);
            JsonNode dataNode = rootNode.path("data");

            int id = dataNode.path("id").asInt();
            String firstName = dataNode.path("first_name").asText();
            String lastName = dataNode.path("last_name").asText();
            String teamName = dataNode.path("team").path("name").asText();
            String position = dataNode.path("position").asText();

            Stats stats1 = new Stats(id, lastName, firstName, position, teamName);
            System.out.println(stats1.toString());
            twitterClient.postTweet(stats1.toString());
        } /*We upload MVP data*/
        else {
            String MVP = """
                    Predicted top 5 MVPS of the current NBA Season:
                    1)Nikola J., PTS:26.4 , AST:9.0, TRB:19.8
                    2)Shai G-A., PTS:30.1 , AST:6.2, TRB:9.1
                    3)Giannis A., PTS:30.4, AST:6.5, TRB:17.9
                    4)Luka D., PTS:33.9 , AST:9.8, TRB:13.5
                    5)Domantas S., PTS:19.4 , AST:8.2, TRB:21.4""";
            twitterClient.postTweet(MVP);
        }

    }

    /**
     * Returns data of NBA Players through get request
     *
     * @return a message about the players
     */
   public String getData() {
    /*We are storing the count so no reason there should be more than one entry*/
       List<Count> countList = countRepository.findAll();
       int size =countList.size();
       if( size != 1){
           if(size != 0){
               countRepository.deleteAll();
           }
           Count count = new Count(1,1);
           countRepository.save(count);
       }

       Count count = countRepository.getReferenceById(1);
       int position =  count.getCount();
       count.setCount(position + 1);
       countRepository.save(count);
       //Create a get request to nba api
       RestClient restClient = RestClient.create();
       return  restClient.get()
               .uri("https://api.balldontlie.io/v1/players/{position}", position)
               .accept(MediaType.APPLICATION_JSON)
               .header("Authorization", "")
               .retrieve()
               .body(String.class);
   }

    /**
     * Runs through the balldontlie API to store all players in database for future
     * reference
     */
   public void storePlayers() throws JsonProcessingException {

       int NBA_Players_2023 = 539;
       players = new String[539];

       //Create a get request to nba api
       RestClient restClient = RestClient.create();
       for(int player = 0; player < NBA_Players_2023; player++) {
           //local save of players
          players[player] = restClient.get()
                   .uri("https://api.balldontlie.io/v1/players/{position}", player)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("Authorization", "")
                   .retrieve()
                   .body(String.class);

        //Adding player to database
           String stats = getData();
           ObjectMapper mapper = new ObjectMapper();
           JsonNode rootNode = mapper.readTree(players[player]);
           JsonNode dataNode = rootNode.path("data");

           int id = dataNode.path("id").asInt();
           String firstName = dataNode.path("first_name").asText();
           String lastName = dataNode.path("last_name").asText();
           String teamName = dataNode.path("team").path("name").asText();
           String position = dataNode.path("position").asText();

           Stats stats1 = new Stats(id, lastName, firstName, position, teamName);
           statsRepository.save(stats1);

       }

   }

    /**
     * Reads in data from CSV for MVP data for bot to post
     */
   public void readMVPs() {
//       try {
//           FileReader reader = new FileReader("file");
//            CSVReader csvReader = new CSVReader(filereader);
//            String[] record;
//
//
//
//       } catch (FileNotFoundException e) {
//           System.out.println("File error. Please check path or file");
//       }
   }

}
