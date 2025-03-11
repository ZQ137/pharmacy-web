package com.example.pharmacy.admin.drug.service;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.drug.entity.dto.DrugAddDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugQueryDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugUpdateDTO;
import com.example.pharmacy.admin.drug.entity.vo.DrugVO;
import com.example.pharmacy.admin.drug.mapper.DrugMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.common.Result;
import com.example.pharmacy.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrugService {

    @Autowired
    private DrugMapper drugMapper;

    /**
     * 分页查询药品
     *
     * @param queryDTO
     * @return
     */
    public PageResult<DrugVO> getDrugsByConditions(DrugQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getSize());
        List<Drug> drugList = drugMapper.findDrugsByCondition(queryDTO);
        List<DrugVO> drugVOList = drugList.stream()
                .map(drug -> {
                    DrugVO drugVO = new DrugVO();
                    BeanUtils.copyProperties(drug, drugVO);
                    return drugVO;
                })
                .collect(Collectors.toList());
        PageInfo<Drug> pageInfo = new PageInfo<>(drugList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),drugVOList);
    }

    /**
     * 通过 ID 获取药品
     *
     * @param id
     * @return
     */
    public Optional<Drug> getDrugById(Long id) {
        return Optional.ofNullable(drugMapper.findById(id));
    }

    /**
     * 添加药品
     *
     * @param drugAddDTO
     */
    public void saveDrug(DrugAddDTO drugAddDTO) {
        if (drugAddDTO.getName() != null) {
            throw new CustomException(ErrorCode.MEDICINE_ALREADY_EXISTS);
        }
        Drug drug = new Drug();
        BeanUtils.copyProperties(drugAddDTO,drug);
        drug.setCreatedAt(LocalDateTime.now());
        drug.setUpdatedAt(LocalDateTime.now());

        drugMapper.insert(drug);
    }

    /**
     * 根据id删除药品
     *
     * @param id
     * @return
     */
    public void deleteDrug(Long id) {
        if (drugMapper.findById(id) == null) {
            throw new CustomException(ErrorCode.MEDICINE_NOT_FOUND);
        }
        drugMapper.delete(id);
    }

    /**
     * 更新药品
     *
     * @param drugUpdateDTO
     */
    public void updateDrug(DrugUpdateDTO drugUpdateDTO) {
        if (drugMapper.findById(drugUpdateDTO.getId()) == null) {
            throw new CustomException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        Drug drug = new Drug();
        BeanUtils.copyProperties(drugUpdateDTO,drug);
        drug.setUpdatedAt(LocalDateTime.now());

        drugMapper.update(drug);
    }
}
