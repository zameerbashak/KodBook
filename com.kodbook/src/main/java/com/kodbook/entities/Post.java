package com.kodbook.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String caption;
    
    private int likes;
    
    @Column
    private List<String> comments;

    @Lob
    @Basic
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    // Default constructor
    public Post() {
        this.comments = new ArrayList<>(); // Initialize comments as an empty list
    }

    // Constructor with fields
    public Post(Long id, String caption, int likes, List<String> comments, byte[] photo) {
        this.id = id;
        this.caption = caption;
        this.likes = likes;
        this.comments = comments != null ? comments : new ArrayList<>();  // Ensure it's initialized
        this.photo = photo;
    }

    // Getter and setter for comments
    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    // Method to add a single comment to the list
    public void addComment(String comments) {
            this.comments = new ArrayList<>();
        this.comments.add(comments);  // Add new comment to the list
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Convert photo to Base64 for display
    public String getPhotoBase64() {
        if (photo == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(photo);
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", caption=" + caption + ", likes=" + likes + ", comments=" + comments + ", photo="
                + Arrays.toString(photo) + "]";
    }
}
