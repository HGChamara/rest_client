package com.web.http;

import com.web.dto.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserClient {
    @GetExchange
    List<User> users();

    @GetExchange("/{id}")
    User user(@PathVariable int id);
}
