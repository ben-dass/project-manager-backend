package org.theonlydodo.backend.query;

public class RoleQuery {

    public static final String SELECT_ROLE_BY_NAME_QUERY
            = "select * from roles where name = :name";

    public static final String INSERT_ROLE_TO_USER_QUERY
            = "insert into user_roles (user_id, role_id) values (:userId, :roleId)";

}
