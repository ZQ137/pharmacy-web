package com.example.pharmacy.admin.doctor.controller;

import com.example.pharmacy.admin.doctor.entity.dto.DoctorAddDTO;
import com.example.pharmacy.admin.doctor.entity.dto.DoctorUpdateDTO;
import com.example.pharmacy.admin.doctor.entity.vo.DoctorVO;
import com.example.pharmacy.admin.doctor.service.DoctorService;
import com.example.pharmacy.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医生管理控制器
 */
@RestController
@RequestMapping("/api/doctor")
@Tag(name = "医生管理")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    /**
     * 获取所有医生（仅管理员）
     *
     * @return
     */
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "获取所有医生", description = "获取所有医生，仅限管理员查看")
    public Result<List<DoctorVO>> getAllDoctors() {
        List<DoctorVO> result = doctorService.getAllDoctors();
        return Result.success(result);
    }

    /**
     * 获取医生信息
     *
     * @param id 医生ID
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取医生信息", description = "根据医生ID获取医生信息")
    public Result<DoctorVO> getDoctorInfo(@PathVariable Long id) {
        DoctorVO doctorVO = doctorService.getDoctorInfo(id);
        return Result.success(doctorVO);
    }

    /**
     * 添加医生
     *
     * @param doctorAddDTO 医生添加DTO
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "添加医生", description = "添加医生，仅限管理员")
    public Result<?> addDoctor(@RequestBody DoctorAddDTO doctorAddDTO) {
        doctorService.addDoctor(doctorAddDTO);
        return Result.success("医生已添加");
    }

    /**
     * 更新医生信息
     *
     * @param doctorUpdateDTO 医生更新DTO
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "更新医生信息", description = "更新医生信息")
    public Result<?> updateDoctor(@RequestBody DoctorUpdateDTO doctorUpdateDTO) {
        doctorService.updateDoctor(doctorUpdateDTO);
        return Result.success("医生信息已更新");
    }

    /**
     * 删除医生
     *
     * @param id 医生ID
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除医生", description = "删除医生")
    public Result<?> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return Result.success("医生删除成功");
    }
}
