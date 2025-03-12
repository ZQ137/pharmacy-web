package com.example.pharmacy.admin.consultation.mapper;

import com.example.pharmacy.admin.consultation.entity.Consultation;
import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationDiagnoseDTO;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationQueryDTO;
import com.example.pharmacy.admin.consultation.entity.vo.ConsultationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ConsultationMapper {
    /**
     * 根据查询条件获取问诊记录
     */
    List<ConsultationVO> findConsultationsByConditions(@Param("query") ConsultationQueryDTO queryDTO);

    /**
     * 获取问诊记录详情
     */
    @Select("SELECT * FROM consultation WHERE id = #{#id}")
    Optional<ConsultationVO> findById(@Param("id") Long id);

    /**
     * 创建问诊
     */
    void insertConsultation(Consultation consultation);

    /**
     * 更新问诊记录
     */
    void updateConsultation(ConsultationDiagnoseDTO diagnoseDTO);

    /**
     * 更新问诊状态
     */
    @Update("UPDATE consultation SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") ConsultationStatus status);
}
