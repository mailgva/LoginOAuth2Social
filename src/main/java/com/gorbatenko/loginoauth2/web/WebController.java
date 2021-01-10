package com.gorbatenko.loginoauth2.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class WebController {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @GetMapping("/")
    public String root(OAuth2AuthenticationToken authenticationToken, Model model) {
        String name = authenticationToken.getPrincipal().getAttribute("name");
        String email = getUserEmail(authenticationToken);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "/welcome";
    }

    public String getUserEmail(OAuth2AuthenticationToken authenticationToken) {
        String email = authenticationToken.getPrincipal().getAttribute("email");

         /*
            For github when email is hide, need send additional request for get email
         */
        if (email == null && "github".equals(authenticationToken.getAuthorizedClientRegistrationId())) {
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                    authenticationToken.getAuthorizedClientRegistrationId(),
                    authenticationToken.getName());

            String token = client.getAccessToken().getTokenValue();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Accept", "application/vnd.github.v3+json");
            headers.add("Authorization", "token " + token);

            HttpEntity<?> entity = new HttpEntity<Object>(headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<JsonNode> entityUser = restTemplate.exchange("https://api.github.com/user/emails", HttpMethod.GET, entity, JsonNode.class);

            email = entityUser.getBody().findValue("email").asText();
        }

        return email;
    }

}
