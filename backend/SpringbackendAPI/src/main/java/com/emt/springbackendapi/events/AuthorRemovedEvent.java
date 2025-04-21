package com.emt.springbackendapi.events;

import com.emt.springbackendapi.model.domain.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
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