package com.web.controller;

import com.web.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {
     public static List<User> users = new ArrayList<>();

    @GetMapping()
    public List<User> getUsers() {
        return users;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable int id) {
        User user1 = users.get(id);
        return user1;
    }

    @PostMapping()
    public User addUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

}
