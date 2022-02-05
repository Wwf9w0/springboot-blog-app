package com.blog.app.controller;

import com.blog.app.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("login")
    public ResponseEntity<Principal> signIn(Principal principal){
        return ResponseEntity.ok(principal);
    }
}
