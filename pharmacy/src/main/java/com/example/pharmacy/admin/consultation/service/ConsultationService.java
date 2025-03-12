package com.example.pharmacy.admin.consultation.service;

import com.example.pharmacy.admin.consultation.entity.Consultation;
import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationAddDTO;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationDiagnoseDTO;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationQueryDTO;
import com.example.pharmacy.admin.consultation.entity.vo.ConsultationVO;
import com.example.pharmacy.admin.consultation.mapper.ConsultationMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationMapper consultationMapper;

    /**
     * 获取所有问诊记录（分页）
     */
    public PageResult<ConsultationVO> getAllConsultations(ConsultationQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize());
        List<ConsultationVO> consultations = consultationMapper.findConsultationsByConditions(queryDTO);
        PageInfo<ConsultationVO> pageInfo = new PageInfo<>(consultations);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), consultations);
    }

    /**
     * 获取单个问诊记录
     */
    public ConsultationVO getConsultationById(Long id) {
        return consultationMapper.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_NOT_FOUND));
    }

    /**
     * 创建问诊记录
     */
    public void createConsultation(ConsultationAddDTO addDTO) {
        Consultation consultation = new Consultation();
        BeanUtils.copyProperties(addDTO, consultation);
        consultation.setStatus(ConsultationStatus.PENDING);
        consultationMapper.insertConsultation(consultation);
    }

    /**
     * 医生填写诊断
     */
    public void diagnoseConsultation(ConsultationDiagnoseDTO diagnoseDTO) {
        consultationMapper.findById(diagnoseDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONSULTATION_NOT_FOUND));

        consultationMapper.updateConsultation(diagnoseDTO);
    }

    /**
     * 更新问诊状态
     */
    public void updateConsultationStatus(Long id, ConsultationStatus status) {
        consultationMapper.updateStatus(id, status);
    }
}
