package com.example.springboots.webservice.user;

import  jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class  UserJpaController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent() ) {
            throw new UserNotFoundException("id-" + id);
        }
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Objects> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path = "users/{id}")
    public void deleteById(@PathVariable int id){
        userRepository.deleteById(id);
    }
    @GetMapping(path = "/users/{id}/posts")
    public List<Post> retrieveUserPost(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent() ) {
            throw new UserNotFoundException("id-" + id);
        } 
        return user.get().getPosts();
    }
    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<Objects> createUserPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent() ) {
            throw new UserNotFoundException("id-" + id);
        }
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
