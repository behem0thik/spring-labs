package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String home(Model model) {
        Collection<Post> posts = postRepository.findAll();
        model.addAttribute("title", "Блог");
        model.addAttribute("posts", posts);
        System.out.println("Got " + posts.size() + " posts");
        return "blog";
    }

    @GetMapping("/blog/add")
    public String add(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String text, Model model) {
        Post post = new Post(title, anons, text);
        if (!post.getTitle().isBlank() && !post.getAnons().isBlank() && !post.getText().isBlank()) {
            postRepository.save(post);
        }
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String getById(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Post post = postRepository.getReferenceById(id);
        List<Post> foundPosts = new ArrayList<>();
        foundPosts.add(post);

        if (foundPosts.size() > 0) {
            foundPosts.get(0).setViews(foundPosts.get(0).getViews() + 1);
        }

        model.addAttribute("title", "Блог");
        model.addAttribute("posts", foundPosts);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/update")
    public String update(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Post post = postRepository.getReferenceById(id);
        List<Post> foundPosts = new ArrayList<>();
        foundPosts.add(post);

        model.addAttribute("post", foundPosts);
        return "blog-update";
    }

    @PostMapping("/blog/{id}/update")
    public String updateItem(@PathVariable(value = "id") long id, @RequestParam String title,
                             @RequestParam String anons, @RequestParam String text, Model model) {
        Post post = postRepository.getReferenceById(id);
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);


        if (!post.getTitle().isBlank() && !post.getAnons().isBlank() && !post.getText().isBlank()) {
            postRepository.save(post);
        }
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String remove(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        postRepository.deleteById(id);
        return "redirect:/blog";
    }

}
