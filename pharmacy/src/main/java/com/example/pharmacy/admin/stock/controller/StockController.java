package com.example.pharmacy.admin.stock.controller;

import com.example.pharmacy.admin.stock.entity.dto.StockAdjustmentDTO;
import com.example.pharmacy.admin.stock.entity.vo.DrugStockVO;
import com.example.pharmacy.admin.stock.service.StockService;
import com.example.pharmacy.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "药品库存监控")
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * 获取所有药品库存信息
     */
    @GetMapping("/list")
    @Operation(summary = "获取药品库存", description = "获取所有药品的库存信息")
    public Result<List<DrugStockVO>> getAllStock() {
        return Result.success(stockService.getAllStock());
    }

    /**
     * 获取单个药品库存详情
     */
    @GetMapping("/{drugId}")
    @Operation(summary = "获取单个药品库存", description = "查询某个药品的库存情况")
    public Result<DrugStockVO> getStock(@PathVariable Long drugId) {
        return Result.success(stockService.getStockById(drugId));
    }

    /**
     * 获取库存低于预警值的药品
     */
    @GetMapping("/low-stock")
    @Operation(summary = "库存预警", description = "获取库存低于预警阈值的药品")
    public Result<List<DrugStockVO>> getLowStockDrugs(@RequestParam(defaultValue = "10") int threshold) {
        return Result.success(stockService.getLowStockDrugs(threshold));
    }

    /**
     * 库存调整（采购/销售/手动调整）
     */
    @PostMapping("/adjust")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "库存调整", description = "调整药品库存（采购、销售、手动调整）")
    public Result<?> adjustStock(@RequestBody StockAdjustmentDTO stockAdjustmentDTO) {
        stockService.adjustStock(stockAdjustmentDTO);
        return Result.success("库存调整成功");
    }
}
