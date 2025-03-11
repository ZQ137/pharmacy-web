package com.example.pharmacy.admin.user.mapper;

import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.entity.dto.UserQueryDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询单个用户
     * @param username
     * @return
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    /**
     * 条件查询多个用户
     *
     * @return
     */
    List<User> findUsersByConditions(@Param("query") UserQueryDTO queryDTO);

    /**
     * 插入用户
     *
     * @param user
     */
    @Insert("INSERT INTO user (username, password, email, phone, role, name, insurance_number,status, created_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{role}, #{name},#{insuranceNumber},#{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    @Update("UPDATE user SET email = #{email}, phone = #{phone}, insurance_number = #{insuranceNumber}, name = #{name},status = #{status} WHERE id = #{id}")
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Integer id);
}
