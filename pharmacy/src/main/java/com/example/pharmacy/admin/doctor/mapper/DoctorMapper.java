package com.example.pharmacy.admin.doctor.mapper;

import com.example.pharmacy.admin.doctor.entity.Doctor;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DoctorMapper {
    /**
     * 获取所有医生
     *
     * @return
     */
    @Select("SELECT * FROM doctor")
    List<Doctor> findAllDoctors();

    /**
     * 根据医生ID查找医生
     *
     * @param id 医生ID
     * @return
     */
    @Select("SELECT * FROM doctor WHERE id = #{id}")
    Optional<Doctor> findDoctorById(Long id);

    /**
     * 插入医生
     *
     * @param doctor
     */
    @Insert("INSERT INTO doctor (user_id, license_number, department, hospital, years_of_experience) " +
            "VALUES (#{userId}, #{licenseNumber}, #{department}, #{hospital}, #{yearsOfExperience})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertDoctor(Doctor doctor);

    /**
     * 更新医生
     *
     * @param doctor
     */
    @Update("UPDATE doctor SET license_number = #{licenseNumber}, department = #{department}, " +
            "hospital = #{hospital}, years_of_experience = #{yearsOfExperience} WHERE id = #{id}")
    void updateDoctor(Doctor doctor);

    /**
     * 删除医生
     *
     * @param id
     */
    @Delete("DELETE FROM doctor WHERE id = #{id}")
    void deleteDoctor(Long id);
}
