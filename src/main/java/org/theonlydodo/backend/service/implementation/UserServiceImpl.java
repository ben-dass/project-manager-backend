package org.theonlydodo.backend.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.theonlydodo.backend.dto.UserDTO;
import org.theonlydodo.backend.dtomapper.UserDTOMapper;
import org.theonlydodo.backend.model.User;
import org.theonlydodo.backend.repository.UserRepository;
import org.theonlydodo.backend.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository< User > userRepository;

    @Override
    public UserDTO createUser( User user ) {
        return UserDTOMapper.fromUser( userRepository.create( user ) );
    }
}
