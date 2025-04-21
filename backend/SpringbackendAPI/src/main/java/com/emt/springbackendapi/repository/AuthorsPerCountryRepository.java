package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.domain.AuthorsPerCountryMView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorsPerCountryRepository extends JpaRepository<AuthorsPerCountryMView, String> {
    Optional<AuthorsPerCountryMView> findAuthorsPerCountryMViewByCountryName(String countryName);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.authors_per_country", nativeQuery = true)
    void refreshMaterializedView();
}
