package com.example.pharmacy.admin.stock.mapper;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.stock.entity.StockLog;
import com.example.pharmacy.admin.stock.entity.vo.DrugStockVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StockLogMapper {

    // 记录库存变更日志
    void insertStockLog(StockLog stockLog);

    // 获取所有药品库存信息
    List<DrugStockVO> findAllStock();

    // 获取单个药品库存信息
    Optional<DrugStockVO> findStockById(@Param("drugId") Long drugId);

    // 获取库存低于预警阈值的药品
    List<DrugStockVO> findLowStockDrugs(@Param("threshold") int threshold);

    // 根据药品ID查询药品
    Optional<Drug> findById(@Param("drugId") Long drugId);

    // 更新库存
    void updateStock(@Param("drugId") Long drugId, @Param("stock") int stock);
}
