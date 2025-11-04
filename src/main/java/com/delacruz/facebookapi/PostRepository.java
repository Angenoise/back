package com.delacruz.facebookapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // You can add custom query methods here if needed, for example:
    // List<Post> findByAuthor(String author);
    // Spring Data JPA will automatically understand what you mean.
}
