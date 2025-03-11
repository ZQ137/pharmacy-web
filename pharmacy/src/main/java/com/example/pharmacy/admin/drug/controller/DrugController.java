package com.example.pharmacy.admin.drug.controller;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.drug.entity.dto.DrugAddDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugQueryDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugUpdateDTO;
import com.example.pharmacy.admin.drug.entity.vo.DrugVO;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.common.Result;
import com.example.pharmacy.admin.drug.service.DrugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/drugs")
@Tag(name = "药品管理")
public class DrugController {

    @Autowired
    private DrugService drugService;

    /**
     * 分页查询药品
     *
     * @param queryDTO
     * @return
     */
    @GetMapping
    @Operation(summary = "条件查询药品",description = "根据前端传递的查询条件，分页查询")
    public Result<PageResult<DrugVO>> getDrugsByConditions(DrugQueryDTO queryDTO){
        PageResult<DrugVO> drugList = drugService.getDrugsByConditions(queryDTO);
        return Result.success(drugList);
    }

    /**
     * 通过 ID 获取药品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询药品",description = "根据id查询")
    public Result<Drug> getDrugById(@PathVariable Long id){
        Optional<Drug> drug = drugService.getDrugById(id);
        return drug.map(Result::success).orElseGet(() -> Result.error(ErrorCode.MEDICINE_NOT_FOUND));
    }

    /**
     * 添加药品
     *
     * @param drugAddDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "添加药品",description = "添加药品")
    public Result<?> createDrug(@RequestBody DrugAddDTO drugAddDTO) {
        drugService.saveDrug(drugAddDTO);
        return Result.success("药品已添加");
    }

    /**
     * 修改药品
     *
     * @param id
     * @param drugUpdateDTO
     * @return
     */
    @PutMapping("/{id}")
    @Operation(summary = "添加药品",description = "添加药品")
    public Result<?> updateDrug(@PathVariable Long id, @RequestBody DrugUpdateDTO drugUpdateDTO) {
        drugService.updateDrug(drugUpdateDTO);
        return Result.success("修改成功");
    }

    /**
     * 删除药品
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除药品",description = "删除药品")
    public Result<?> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return Result.success("删除成功");
    }

}
