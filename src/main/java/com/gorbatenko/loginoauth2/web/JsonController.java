package com.gorbatenko.loginoauth2.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class JsonController {
    @GetMapping("/jsona")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/jsonp")
    public Principal authentication(Principal principal) {
        return principal;
    }

    //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
}
