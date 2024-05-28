package org.theonlydodo.backend.service;

import org.theonlydodo.backend.dto.UserDTO;
import org.theonlydodo.backend.model.User;

public interface UserService {

    UserDTO createUser( User user );

}
