package org.theonlydodo.backend.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.theonlydodo.backend.exception.ApiException;
import org.theonlydodo.backend.model.Role;
import org.theonlydodo.backend.repository.RoleRepository;
import org.theonlydodo.backend.rowmapper.RoleRowMapper;

import java.util.Collection;

import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static org.theonlydodo.backend.enumeration.RoleType.ROLE_USER;
import static org.theonlydodo.backend.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static org.theonlydodo.backend.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository< Role > {


    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create( Role data ) {
        return null;
    }

    @Override
    public Collection list( int page, int pageSize ) {
        return null;
    }

    @Override
    public Role get( Long id ) {
        return null;
    }

    @Override
    public Role update( Role data ) {
        return null;
    }

    @Override
    public Boolean delete( Long id ) {
        return null;
    }

    @Override
    public void addRoleToUser( Long userId, String roleName ) {

        log.info( "Adding role {} to user id: {}", roleName, userId );

        try {

            Role role = jdbc.queryForObject( SELECT_ROLE_BY_NAME_QUERY, of( "name", roleName ), new RoleRowMapper() );
            jdbc.update( INSERT_ROLE_TO_USER_QUERY, of( "userId", userId, "roleId", requireNonNull( role ).getId() ) );

        } catch ( EmptyResultDataAccessException exception ) {
            throw new ApiException( "No role found by name: " + ROLE_USER.name() );
        } catch ( Exception exception ) {
            throw new ApiException( "An error occurred. Please try again" );
        }
    }

    @Override
    public Role getRoleByUserId( Long userId ) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail( String email ) {
        return null;
    }

    @Override
    public void updateUserRole( Long userId, String roleName ) {

    }
}
