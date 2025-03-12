package com.example.pharmacy.admin.consultation.controller;

import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationAddDTO;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationDiagnoseDTO;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationQueryDTO;
import com.example.pharmacy.admin.consultation.entity.vo.ConsultationVO;
import com.example.pharmacy.admin.consultation.service.ConsultationService;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultation")
@Tag(name = "问诊管理")
public class ConsultationController {
    @Autowired
    private
    ConsultationService consultationService;

    /**
     * 获取所有问诊记录（可按条件筛选）
     */
    @GetMapping()
    @Operation(summary = "获取所有问诊记录", description = "获取所有问诊记录，可按状态、用户、医生等筛选")
    public Result<PageResult<ConsultationVO>> getAllConsultations(ConsultationQueryDTO queryDTO) {
        PageResult<ConsultationVO> result = consultationService.getAllConsultations(queryDTO);
        return Result.success(result);
    }

    /**
     * 获取单个问诊记录详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @Operation(summary = "获取问诊详情", description = "获取指定问诊记录详情")
    public Result<ConsultationVO> getConsultation(@PathVariable Long id) {
        ConsultationVO consultation = consultationService.getConsultationById(id);
        return Result.success(consultation);
    }

    /**
     * 用户发起问诊
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "发起问诊", description = "患者创建问诊记录")
    public Result<?> createConsultation(@RequestBody ConsultationAddDTO addDTO) {
        consultationService.createConsultation(addDTO);
        return Result.success("问诊已创建");
    }

    /**
     * 医生填写诊断
     */
    @PutMapping("/diagnose")
    @PreAuthorize("hasRole('DOCTOR')")
    @Operation(summary = "医生填写诊断", description = "医生对问诊记录进行诊断")
    public Result<?> diagnoseConsultation(@RequestBody ConsultationDiagnoseDTO diagnoseDTO) {
        consultationService.diagnoseConsultation(diagnoseDTO);
        return Result.success("问诊已诊断");
    }

    /**
     * 更新问诊状态
     */
    @PutMapping("/updateStatus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @Operation(summary = "更新问诊状态", description = "管理员或医生更新问诊状态")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam ConsultationStatus status) {
        consultationService.updateConsultationStatus(id, status);
        return Result.success("问诊状态已更新");
    }
}
