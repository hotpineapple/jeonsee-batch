package com.jeonsee.jeonsee.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "alarm")
public class Alarm {
    private String word;
    private String email;

    public Alarm(String word, String email){
        this.word = word;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "word='" + word + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
