package com.emt.springbackendapi.events;

import com.emt.springbackendapi.model.domain.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Getter
@Profile("psql")
public class AuthorCreatedEvent extends ApplicationEvent {

    private final LocalDateTime when;

    public AuthorCreatedEvent(Author source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public AuthorCreatedEvent(Author source, LocalDateTime when) {
        super(source);
        this.when = when;
    }


}
