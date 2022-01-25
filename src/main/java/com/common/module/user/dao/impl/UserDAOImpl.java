package com.common.module.user.dao.impl;

import com.common.module.user.dao.UserDAO;
import com.common.module.user.dto.User;
import com.common.module.user.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder encoder;

     static final String INSERT = "insert into users (email,password,first_name,last_name,create_by,update_by) values(?,?,?,?,?,?)";
     static final String GET_BY_EMAIL = "select * from users where email=?";
     static final String UPDATE = "update users set first_name=?,last_name=?,update_by=?  where id=?";
     static final String SELECT_ALL = "select * from users";
     static final String SELECT_BY_ID = "select * from users where id = ?";

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_BY_EMAIL,new Object[]{email}, new UserRowMapper());
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID,new Object[] {id},new UserRowMapper());
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public int save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2,encoder.encode(user.getPassword()));
            ps.setString(3,user.getFirstName());
            ps.setString(4,user.getLastName());
            ps.setInt(5,user.getCreateBy());
            ps.setInt(6,user.getUpdateBy());
            return ps;
        }, keyHolder);
        return  keyHolder.getKey().intValue();
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE,user.getFirstName(),user.getLastName(),user.getUpdateBy(),user.getId());
    }
}
