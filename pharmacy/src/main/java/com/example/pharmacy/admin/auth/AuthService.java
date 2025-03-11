package com.example.pharmacy.admin.auth;

import com.example.pharmacy.admin.auth.entity.dto.LoginDTO;
import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.entity.dto.UserRegisterDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.exception.CustomException;
import com.example.pharmacy.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 注册
     *
     * @param userRegisterDTO
     */
    public void registerUser(UserRegisterDTO userRegisterDTO) {

        //检查用户名是否已存在
        if (userMapper.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword())); //加密
        user.setEmail(userRegisterDTO.getEmail());
        user.setPhone(userRegisterDTO.getPhone());
        user.setRole("PATIENT");    //只能注册PATIENT(患者)角色
        user.setCreatedAt(LocalDateTime.now());

        userMapper.insertUser(user);
    }

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    public UserVO login(@Valid LoginDTO loginDTO) {
        Optional<User> userOptional = userMapper.findByUsername(loginDTO.getUsername());

        //用户名不存在
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userOptional.get();

        //密码不匹配
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_ERROR);
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setToken(token);

        return userVO;
    }
}
