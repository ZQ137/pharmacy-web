package com.example.pharmacy.admin.doctor;

import com.example.pharmacy.admin.doctor.entity.Doctor;
import com.example.pharmacy.admin.doctor.entity.dto.DoctorAddDTO;
import com.example.pharmacy.admin.doctor.entity.dto.DoctorUpdateDTO;
import com.example.pharmacy.admin.doctor.entity.vo.DoctorVO;
import com.example.pharmacy.admin.doctor.mapper.DoctorMapper;
import com.example.pharmacy.admin.doctor.service.DoctorService;
import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DocterTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private UserMapper userMapper;

    /**
     * 测试获取所有医生
     */
    @Test
    public void testGetAllDoctors() {
        // 模拟医生数据
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setUserId(10L);
        doctor1.setLicenseNumber("ABC123");
        doctor1.setDepartment("内科");
        doctor1.setHospital("人民医院");
        doctor1.setYearOfExperience(10);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setUserId(20L);
        doctor2.setLicenseNumber("XYZ789");
        doctor2.setDepartment("外科");
        doctor2.setHospital("中心医院");
        doctor2.setYearOfExperience(15);

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        // 模拟用户数据
        User user1 = new User();
        user1.setId(10L);
        user1.setUsername("doctorA");

        User user2 = new User();
        user2.setId(20L);
        user2.setUsername("doctorB");

        // 模拟返回数据
        Mockito.when(doctorMapper.findAllDoctors()).thenReturn(doctors);
        Mockito.when(userMapper.findById(10L)).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.findById(20L)).thenReturn(Optional.of(user2));

        // 调用 service 方法
        List<DoctorVO> doctorVOList = doctorService.getAllDoctors();

        // 验证结果
        assertNotNull(doctorVOList);
        assertEquals(2, doctorVOList.size());
        assertEquals("doctorA", doctorVOList.get(0).getUsername());
        assertEquals("doctorB", doctorVOList.get(1).getUsername());
    }

    /**
     * 测试获取医生信息
     */
    @Test
    public void testGetDoctorInfo() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setUserId(10L);
        doctor.setLicenseNumber("ABC123");
        doctor.setDepartment("内科");
        doctor.setHospital("人民医院");
        doctor.setYearOfExperience(10);

        User user = new User();
        user.setId(10L);
        user.setUsername("doctorA");

        Mockito.when(doctorMapper.findDoctorById(1L)).thenReturn(Optional.of(doctor));
        Mockito.when(userMapper.findById(10L)).thenReturn(Optional.of(user));

        DoctorVO doctorVO = doctorService.getDoctorInfo(1L);

        assertNotNull(doctorVO);
        assertEquals("doctorA", doctorVO.getUsername());
        assertEquals("ABC123", doctorVO.getLicenseNumber());
    }

    /**
     * 测试添加医生
     */
    @Test
    public void testAddDoctor() {
        DoctorAddDTO doctorAddDTO = new DoctorAddDTO();
        doctorAddDTO.setUsername("doctorC");
        doctorAddDTO.setPassword("pass123");
        doctorAddDTO.setName("张三");
        doctorAddDTO.setEmail("doctorC@example.com");
        doctorAddDTO.setPhone("13812345678");
        doctorAddDTO.setLicenseNumber("DEF456");
        doctorAddDTO.setDepartment("皮肤科");
        doctorAddDTO.setHospital("协和医院");
        doctorAddDTO.setYearsOfExperience(8);

        // 调用 addDoctor 方法
        doctorService.addDoctor(doctorAddDTO);

        // 验证插入操作被执行
        Mockito.verify(doctorMapper, Mockito.times(1)).insertDoctor(Mockito.any(Doctor.class));
    }

    /**
     * 测试更新医生信息
     */
    @Test
    public void testUpdateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setUserId(10L);
        doctor.setLicenseNumber("ABC123");
        doctor.setDepartment("内科");
        doctor.setHospital("人民医院");
        doctor.setYearOfExperience(10);

        DoctorUpdateDTO updateDTO = new DoctorUpdateDTO();
        updateDTO.setId(1L);
        updateDTO.setDepartment("骨科");
        updateDTO.setHospital("三甲医院");
        updateDTO.setYearsOfExperience(12);

        Mockito.when(doctorMapper.findDoctorById(1L)).thenReturn(Optional.of(doctor));

        doctorService.updateDoctor(updateDTO);

        // 验证更新操作
        Mockito.verify(doctorMapper, Mockito.times(1)).updateDoctor(Mockito.any(Doctor.class));
    }

    /**
     * 测试删除医生
     */
    @Test
    public void testDeleteDoctor() {
        doctorService.deleteDoctor(1L);

        // 验证删除方法调用了一次
        Mockito.verify(doctorMapper, Mockito.times(1)).deleteDoctor(1L);
    }
}
