package com.delacruz.facebookapi;

import jakarta.persistence.*; // Using Jakarta Persistence
import java.time.LocalDateTime;

/**
 * This is our Entity class, which represents the 'posts' table in the database.
 * A new 'Post' object will map to a new row in that table.
 */
@Entity
@Table(name = "posts") // Explicitly name the table 'posts'
public class Post {

    // --- Fields ---

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    private String author;

    // Use columnDefinition="TEXT" to allow for very long posts
    @Column(columnDefinition = "TEXT")
    private String postContent;

    private String imageUrl;

    // These fields will be managed automatically
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // --- Automatic Date Handling ---

    /**
     * This method is automatically called by JPA just before a new Post
     * is saved (persisted) to the database for the first time.
     */
    @PrePersist
    protected void onCreate() {
        // Set both created and modified dates to the current time
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    /**
     * This method is automatically called by JPA just before an existing Post
     * is updated in the database.
     */
    @PreUpdate
    protected void onUpdate() {
        // Only update the modified date
        modifiedDate = LocalDateTime.now();
    }

    // --- Constructors ---

    /**
     * A no-argument constructor is required by JPA.
     */
    public Post() {
    }

    // --- Getters and Setters ---
    // These are necessary for JPA and Spring to access the fields.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
