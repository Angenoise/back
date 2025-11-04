package com.delacruz.facebookapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is the REST Controller. It defines the API endpoints
 * that clients (like a web or mobile app) can call.
 *
 * @RestController marks this class as a controller where every method
 * returns a domain object instead of a view.
 * @RequestMapping("/api/posts") sets the base URL for all endpoints in this class.
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    // Spring's @Autowired will automatically 'inject' an instance
    // of PostRepository here so we can use it.
    @Autowired
    private PostRepository postRepository;

    /**
     * CREATE: Create a new post.
     * Maps to: POST /api/posts
     * @param post The Post object from the request body (JSON).
     * @return The saved Post object (which will include the new 'id' and 'createdDate').
     */
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // The .save() method will trigger the @PrePersist on the Post entity.
        return postRepository.save(post);
    }

    /**
     * READ: Get all posts.
     * Maps to: GET /api/posts
     * @return A list of all Post objects in the database.
     */
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * READ: Get a single post by its ID.
     * Maps to: GET /api/posts/{id}
     * @param id The ID from the URL path.
     * @return A ResponseEntity containing the Post if found, or a 404 Not Found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        // A more modern way (Java 8+):
        // return postRepository.findById(id)
        //         .map(ResponseEntity::ok) // If present, wrap in ResponseEntity.ok()
        //         .orElse(ResponseEntity.notFound().build()); // Otherwise, build a 404 response
    }

    /**
     * UPDATE: Update an existing post by its ID.
     * Maps to: PUT /api/posts/{id}
     * @param id The ID of the post to update.
     * @param postDetails The updated Post object from the request body (JSON).
     * @return A ResponseEntity containing the updated Post, or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post existingPost = optionalPost.get();
        // Update the fields from the request
        existingPost.setAuthor(postDetails.getAuthor());
        existingPost.setPostContent(postDetails.getPostContent());
        existingPost.setImageUrl(postDetails.getImageUrl());
        // Note: We don't update createdDate.
        // The .save() method will trigger the @PreUpdate on the entity.

        Post updatedPost = postRepository.save(existingPost);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * DELETE: Delete a post by its ID.
     * Maps to: DELETE /api/posts/{id}
     * @param id The ID of the post to delete.
     * @return A ResponseEntity with a 204 No Content status, or 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            // Check if the post exists before trying to delete
            return ResponseEntity.notFound().build();
        }

        postRepository.deleteById(id);
        // Return 204 No Content, indicating success but no body.
        return ResponseEntity.noContent().build();
    }
}
