package com.emt.springbackendapi.listeners;

import com.emt.springbackendapi.events.AuthorCreatedEvent;
import com.emt.springbackendapi.events.AuthorRemovedEvent;
import com.emt.springbackendapi.events.AuthorUpdatedEvent;
import com.emt.springbackendapi.service.AuthorsPerCountryService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventsListener {
    private final AuthorsPerCountryService authorService;

    public AuthorEventsListener(AuthorsPerCountryService authorService) {
        this.authorService = authorService;
    }

    @EventListener
    public void onAuthorCreated(AuthorCreatedEvent e) {
        this.authorService.refreshMaterializedView();
    }

    @EventListener
    public void onAuthorRemoved(AuthorRemovedEvent e) {
        this.authorService.refreshMaterializedView();
    }

    @EventListener
    public void onAuthorUpdated(AuthorUpdatedEvent e) {
        this.authorService.refreshMaterializedView();
    }
}
