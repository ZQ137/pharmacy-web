package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Drug;
import com.example.pharmacy.entity.dto.PageDTO;
import com.example.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {
    @Autowired
    private DrugService drugService;

    // 分页获取药品（支持搜索）
    @GetMapping
    public ResponseEntity<PageDTO<Drug>> getAllDrugs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        Page<Drug> drugsPage = drugService.getAllDrugs(search, page, limit);
        return ResponseEntity.ok(new PageDTO<>(drugsPage));
    }

    // 通过 ID 获取药品
    @GetMapping("/{id}")
    public ResponseEntity<Drug> getDrugById(@PathVariable Integer id) {
        Optional<Drug> drug = drugService.getDrugById(id);
        return drug.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 添加或更新药品
    @PostMapping
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) {
        return ResponseEntity.ok(drugService.saveDrug(drug));
    }

    // 修改药品
    @PutMapping("/{id}")
    public ResponseEntity<Drug> updateDrug(@PathVariable Integer id, @RequestBody Drug drug) {
        if (!drugService.getDrugById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        drug.setId(id);
        return ResponseEntity.ok(drugService.saveDrug(drug));
    }

    // 删除药品
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Integer id) {
        if (!drugService.deleteDrug(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
