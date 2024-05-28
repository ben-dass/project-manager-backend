package org.theonlydodo.backend.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.theonlydodo.backend.exception.ApiException;
import org.theonlydodo.backend.model.Role;
import org.theonlydodo.backend.model.User;
import org.theonlydodo.backend.repository.RoleRepository;
import org.theonlydodo.backend.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.theonlydodo.backend.enumeration.RoleType.ROLE_USER;
import static org.theonlydodo.backend.enumeration.VerificationType.ACCOUNT;
import static org.theonlydodo.backend.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository< User > {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository< Role > roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create( User user ) {

        // Check if email is unique
        if ( getEmailCount( user.getEmail().trim().toLowerCase() ) > 0 ) {
            throw new ApiException( "Email already in use. Please use a different email and try again." );
        }

        // Save new user
        try {

            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource( user );
            jdbc.update( INSERT_USER_QUERY, parameters, holder );
            user.setId( requireNonNull( holder.getKey() ).longValue() );

            // Add role to user
            roleRepository.addRoleToUser( user.getId(), ROLE_USER.name() );

            // Send verification URL
            String verificationUrl = getVerificationUrl( UUID.randomUUID().toString(), ACCOUNT.getType() );

            // Save verification URL
            jdbc.update( INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of( "userId", user.getId(), "url", verificationUrl ) );

            // Send email to user with verification URL
            // emailService.sendVerificationUrl( user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled( false );
            user.setNotLocked( true );

            return user;

        } catch ( Exception exception ) {
            log.error( exception.getMessage() );
            throw new ApiException( "An error occurred. Please try again" );
        }

    }


    @Override
    public Collection< User > list( int page, int pageSize ) {
        return null;
    }

    @Override
    public User get( Long id ) {
        return null;
    }

    @Override
    public User update( User data ) {
        return null;
    }

    @Override
    public Boolean delete( Long id ) {
        return null;
    }

    private Integer getEmailCount( String email ) {
        return jdbc.queryForObject( COUNT_USER_EMAIL_QUERY, Map.of( "email", email ), Integer.class );
    }

    private SqlParameterSource getSqlParameterSource( User user ) {

        return new MapSqlParameterSource()
                .addValue( "firstName", user.getFirstName() )
                .addValue( "lastName", user.getLastName() )
                .addValue( "email", user.getEmail() )
                .addValue( "password", encoder.encode( user.getPassword() ) );

    }

    private String getVerificationUrl( String key, String type ) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path( "/user/verify/" + type + "/" + key ).toUriString();
    }

}





























