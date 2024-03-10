package com.bot.tweetbot;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Count")
public class Count {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int count;

    public Count(int id, int count){
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Count() {
        super();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Count count1 = (Count) o;
        return id == count1.id && count == count1.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }

    @Override
    public String toString() {
        return "Count{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
