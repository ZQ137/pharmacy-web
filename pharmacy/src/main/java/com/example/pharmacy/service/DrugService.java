package com.example.pharmacy.service;

import com.example.pharmacy.entity.Drug;
import com.example.pharmacy.mapper.DrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugService {
    @Autowired
    private DrugMapper drugMapper;

    // 分页查询药品
    public Page<Drug> getAllDrugs(String search, int page, int size) {
        int offset = (page-1) * size;
        List<Drug> drugs = drugMapper.findAll(search, offset, size);
        int total = drugMapper.count(search);
        return new PageImpl<>(drugs, PageRequest.of(page, size), total);
    }

    // 通过 ID 获取药品
    public Optional<Drug> getDrugById(Integer id) {
        return Optional.ofNullable(drugMapper.findById(id));
    }

    // 添加或更新药品
    public Drug saveDrug(Drug drug) {
        if (drug.getId() == null) {
            drugMapper.insert(drug);
        } else {
            drugMapper.update(drug);
        }
        return drug;
    }

    // 删除药品
    public boolean deleteDrug(Integer id) {
        if (drugMapper.findById(id) != null) {
            drugMapper.delete(id);
            return true;
        }
        return false;
    }
}
