package com.example.pharmacy.admin.stock.service;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.stock.entity.StockLog;
import com.example.pharmacy.admin.stock.entity.dto.StockAdjustmentDTO;
import com.example.pharmacy.admin.stock.entity.vo.DrugStockVO;
import com.example.pharmacy.admin.stock.mapper.StockLogMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.exception.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockLogMapper stockLogMapper;

    /**
     * 获取所有药品的库存信息
     */
    public List<DrugStockVO> getAllStock() {
        return stockLogMapper.findAllStock();
    }

    /**
     * 获取单个药品库存信息
     */
    public DrugStockVO getStockById(Long drugId) {
        return stockLogMapper.findStockById(drugId)
                .orElseThrow(() -> new CustomException(ErrorCode.DRUG_NOT_FOUND));
    }

    /**
     * 获取库存低于阈值的药品
     */
    public List<DrugStockVO> getLowStockDrugs(int threshold) {
        return stockLogMapper.findLowStockDrugs(threshold);
    }

    /**
     * 库存调整（采购、销售、手动调整）
     */
    @Transactional
    public void adjustStock(StockAdjustmentDTO stockAdjustmentDTO) {
        Drug drug = stockLogMapper.findById(stockAdjustmentDTO.getDrugId())
                .orElseThrow(() -> new CustomException(ErrorCode.DRUG_NOT_FOUND));

        int newStock = drug.getStock() + stockAdjustmentDTO.getChangeQuantity();
        if (newStock < 0) {
            throw new CustomException(ErrorCode.STOCK_NOT_ENOUGH);
        }

        // 更新库存
        stockLogMapper.updateStock(stockAdjustmentDTO.getDrugId(), newStock);

        // 记录库存变更日志
        StockLog stockLog = new StockLog();
        stockLog.setDrugId(stockAdjustmentDTO.getDrugId());
        stockLog.setChangeType(stockAdjustmentDTO.getChangeType());
        stockLog.setChangeQuantity(stockAdjustmentDTO.getChangeQuantity());
        stockLog.setRemainingStock(newStock);
        stockLogMapper.insertStockLog(stockLog);
    }
}
