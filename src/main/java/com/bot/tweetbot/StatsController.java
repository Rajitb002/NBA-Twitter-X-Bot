package com.bot.tweetbot;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/crud")
public class StatsController {

    private final StatsRepository statsRepository;

    public StatsController(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    /*Equivalent to making an embedded class and adding a getter method
    along with an empty constructor
     */
    public record NewPlayerStatsRequest(
            Integer id,
            String first_name,
            String last_name,
            String position
    ) {
    }

    //Maps the JSON request from client to this method that is then uploaded to the
    //database online through the repository
    @PostMapping(path="/add")
    public @ResponseBody String addPlayerStats(@RequestBody NewPlayerStatsRequest request){
        Stats stats = new Stats();
        stats.setFirst_name(request.first_name);
        stats.setLast_name(request.last_name);
        stats.setPosition(request.position);
        stats.setId(request.id);
        statsRepository.save(stats);
        return "Added " + stats.toString();
    }

    @GetMapping(path="/getAll")
    public @ResponseBody List<Stats> getAllPlayersStats(){
        return statsRepository.findAll();
    }

    @DeleteMapping(path="/deleteAll")
    public void deleteAllPlayers(){statsRepository.deleteAll();}

    @DeleteMapping("{id}")
    public @ResponseBody String deletePlayerStats(@PathVariable("id") Integer id){
        Stats player = statsRepository.getReferenceById(id);
        statsRepository.deleteById(id);
        return "Deleted " + player.toString();
    }

}
