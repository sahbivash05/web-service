package com.example.springboots.webservice.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUser() {
        return userDaoService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Objects> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path = "users/{id}")
    public User deleteById(@PathVariable int id){
        User user = userDaoService.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return user;
    }
}
