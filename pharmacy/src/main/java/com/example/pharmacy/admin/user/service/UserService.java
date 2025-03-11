package com.example.pharmacy.admin.user.service;

import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.entity.dto.UserAddDTO;
import com.example.pharmacy.admin.user.entity.dto.UserQueryDTO;
import com.example.pharmacy.admin.user.entity.dto.UserUpdateDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.exception.CustomException;
import com.example.pharmacy.util.JwtUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取所有用户
     *
     * @return
     */
    public PageResult<UserVO> getAllUsers(UserQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize());
        List<User> users = userMapper.findUsersByConditions(queryDTO);
        List<UserVO> userVOList = users.stream()
                .map(user -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(user, userVO);
                    return userVO;
                })
                .collect(Collectors.toList());
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), userVOList);
    }

    /**
     * 更新用户信息
     *
     * @param token
     * @param userUpdateDTO
     */
    public void updateUser(String token, UserUpdateDTO userUpdateDTO) {
        String username = jwtUtil.getUsernameFromToken(token);

        //检查用户名是否存在
        Optional<User> userOpt = userMapper.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        //更新用户信息
        User user = userOpt.get();
        BeanUtils.copyProperties(userUpdateDTO, user);
        userMapper.updateUser(user);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    public UserVO getUserInfo(String username) {
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return userVO;
    }

    /**
     * 添加用户
     *
     * @param token
     * @param userAddDTO
     */
    public void addUser(String token, UserAddDTO userAddDTO) {
        String username = jwtUtil.getUsernameFromToken(token);

        Optional<User> userOpt = userMapper.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userOpt.get();
        BeanUtils.copyProperties(userAddDTO, user);
        userMapper.insertUser(user);
    }


}
