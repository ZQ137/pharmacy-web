package com.example.pharmacy.mapper;

import com.example.pharmacy.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    @Select("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert("INSERT INTO user (username, password, email, phone, role, name, insurance_number, created_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{role}, #{name},#{insuranceNumber}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE user SET email = #{email}, phone = #{phone}, insurance_number = #{insuranceNumber}, name = #{name} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Integer id);
}
