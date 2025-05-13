package com.emt.springbackendapi.events;

import com.emt.springbackendapi.model.domain.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Getter
@Profile("psql")
public class AuthorRemovedEvent extends ApplicationEvent {

    private final LocalDateTime when;

    public AuthorRemovedEvent(Author source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public AuthorRemovedEvent(Object source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}