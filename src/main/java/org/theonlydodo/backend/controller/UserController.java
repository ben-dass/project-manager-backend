package org.theonlydodo.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.theonlydodo.backend.dto.UserDTO;
import org.theonlydodo.backend.model.HttpResponse;
import org.theonlydodo.backend.model.User;
import org.theonlydodo.backend.service.UserService;

import java.net.URI;
import java.util.Map;

import static java.time.OffsetDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity< HttpResponse > saveUser( @RequestBody @Valid User user ) {
        UserDTO userDTO = userService.createUser( user );
        return ResponseEntity.created( getUri() ).body(
                HttpResponse.builder()
                        .timestamp( now().toString() )
                        .data( Map.of( "user", userDTO ) )
                        .status( CREATED )
                        .statusCode( CREATED.value() )
                        .build()
        );
    }

    private URI getUri() {

        return URI.create( ServletUriComponentsBuilder.fromCurrentContextPath().path( "/user/get/<userId>" ).toUriString() );

    }

}
