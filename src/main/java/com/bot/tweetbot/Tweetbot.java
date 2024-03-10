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
import java.util.List;


@RestController()
@RequestMapping("bot")
public class Tweetbot {

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
   public void mockpost(@RequestBody String data)  {
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

}
