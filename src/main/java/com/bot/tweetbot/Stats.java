package com.bot.tweetbot;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name =  "Stats")
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String first_name,last_name,position,team;
    public Stats(Integer id, String last_name, String first_name, String position,String team){
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.position = position;
        this.team = team;
    }

    public Stats() {
    }

    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player of the day: " +
                 first_name +  " " +
                  last_name  +
                ", Position=" + position  +
                ", Team=" + team  ;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
