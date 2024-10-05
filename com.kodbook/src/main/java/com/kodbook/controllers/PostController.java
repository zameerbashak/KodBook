package com.kodbook.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kodbook.entities.Post;
import com.kodbook.entities.User;
import com.kodbook.services.PostService;
import com.kodbook.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	@Autowired
	PostService service;
	@Autowired
	UserService userService;

	@PostMapping("/createPost")
	public String createPost(@RequestParam("caption") String caption, @RequestParam("photo") MultipartFile photo,
			Model model, HttpSession session) {

		String username = (String) session.getAttribute("username");
		User user = userService.getUser(username);

		Post post = new Post();
		// updating post object
		post.setUser(user);
		post.setCaption(caption);
		try {
			post.setPhoto(photo.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.createPost(post);
		// updating user object
		List<Post> posts = user.getPosts();
		if (posts == null) {
			posts = new ArrayList<Post>();
		}
		posts.add(post);
		user.setPosts(posts);
		userService.updateUser(user);

		List<Post> allPosts = service.fetchAllPosts();
		model.addAttribute("allPosts", allPosts);
		return "home";
	}

	@PostMapping("/likePost")
	public String likePost(@RequestParam Long id, Model model, HttpSession session) {
	    // Get the current user
	    String username = (String) session.getAttribute("username");

	    // Retrieve the post by ID
	    Post post = service.getPost(id);

	    // Initialize the likedByUsers list if it's null
	    if (post.getLikedByUsers() == null) {
	        post.setLikedByUsers(new ArrayList<>()); // Initialize if null
	    }

	    // Check if the user has already liked the post
	    List<String> likedByUsers = post.getLikedByUsers();
	    if (!likedByUsers.contains(username)) {
	        // If user hasn't liked the post, increment likes and add user to likedByUsers
	        post.setLikes(post.getLikes() + 1);
	        likedByUsers.add(username);
	        post.setLikedByUsers(likedByUsers);
	        service.updatePost(post);
	    }

	    // Fetch and display all posts in the view
	    List<Post> allPosts = service.fetchAllPosts();
	    model.addAttribute("allPosts", allPosts);

	    return "home";
	}



	@PostMapping("/addComment")
	public String addComment(@RequestParam Long id, @RequestParam String comment, Model model, HttpSession session) {
	    // Get the logged-in user
	    String username = (String) session.getAttribute("username");
	    User user = userService.getUser(username);

	    // Retrieve the post by ID
	    Post post = service.getPost(id);

	    // Initialize the comment list if it's null
	    List<String> comments = post.getComments();
	    if (comments == null) {
	        comments = new ArrayList<>();
	    }

	    // Add the comment with the username in the format "username: comment"
	    String commentWithUser = username + ": " + comment;
	    comments.add(commentWithUser);

	    // Update post with the new comment list
	    post.setComments(comments);
	    service.updatePost(post);

	    // Fetch and display all posts in the view
	    List<Post> allPosts = service.fetchAllPosts();
	    model.addAttribute("allPosts", allPosts);

	    return "home";
	}
	@GetMapping("/deletePost")
	public String deletePost(@RequestParam Long id) {
		service.deletePost(id);
		return "myProfile";
	}

}
