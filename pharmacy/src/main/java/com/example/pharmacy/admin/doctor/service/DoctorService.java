package com.example.pharmacy.admin.doctor.service;

import com.example.pharmacy.admin.doctor.entity.Doctor;
import com.example.pharmacy.admin.doctor.entity.dto.DoctorAddDTO;
import com.example.pharmacy.admin.doctor.entity.dto.DoctorUpdateDTO;
import com.example.pharmacy.admin.doctor.entity.vo.DoctorVO;
import com.example.pharmacy.admin.doctor.mapper.DoctorMapper;
import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有医生
     *
     * @return
     */
    public List<DoctorVO> getAllDoctors() {
        List<Doctor> doctors = doctorMapper.findAllDoctors();
        return doctors.stream().map(doctor -> {
            DoctorVO doctorVO = new DoctorVO();
            BeanUtils.copyProperties(doctor, doctorVO);

            // 查找关联的用户信息（用户名）
            User user = userMapper.findById(doctor.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            doctorVO.setUsername(user.getUsername());

            return doctorVO;
        }).collect(Collectors.toList());
    }

    /**
     * 获取医生信息
     *
     * @param id
     * @return
     */
    public DoctorVO getDoctorInfo(Long id) {
        Doctor doctor = doctorMapper.findDoctorById(id).orElseThrow(() -> new CustomException(ErrorCode.DOCTOR_NOT_FOUND));

        DoctorVO doctorVO = new DoctorVO();
        BeanUtils.copyProperties(doctor, doctorVO);

        // 查找关联的用户信息
        User user = userMapper.findById(doctor.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        doctorVO.setUsername(user.getUsername());

        return doctorVO;
    }

    /**
     * 添加医生
     *
     * @param doctorAddDTO
     */
    public void addDoctor(DoctorAddDTO doctorAddDTO) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorAddDTO, doctor);

        doctorMapper.insertDoctor(doctor);
    }

    /**
     * 更新医生信息
     *
     * @param doctorUpdateDTO
     */
    public void updateDoctor(DoctorUpdateDTO doctorUpdateDTO) {
        Doctor doctor = doctorMapper.findDoctorById(doctorUpdateDTO.getId()).orElseThrow(() -> new CustomException(ErrorCode.DOCTOR_NOT_FOUND));

        BeanUtils.copyProperties(doctorUpdateDTO, doctor);
        doctorMapper.updateDoctor(doctor);
    }

    /**
     * 删除医生
     *
     * @param id
     */
    public void deleteDoctor(Long id) {
        doctorMapper.deleteDoctor(id);
    }
}
