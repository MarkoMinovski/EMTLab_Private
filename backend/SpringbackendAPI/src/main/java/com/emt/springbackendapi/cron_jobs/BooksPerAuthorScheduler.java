package com.emt.springbackendapi.cron_jobs;

import com.emt.springbackendapi.service.BooksPerAuthorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BooksPerAuthorScheduler {

    private final BooksPerAuthorService booksPerAuthorService;

    public BooksPerAuthorScheduler(BooksPerAuthorService booksPerAuthorService) {
        this.booksPerAuthorService = booksPerAuthorService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refreshBooksPerAuthorMaterializedView() {
        this.booksPerAuthorService.refreshMaterializedView();
    }

}
