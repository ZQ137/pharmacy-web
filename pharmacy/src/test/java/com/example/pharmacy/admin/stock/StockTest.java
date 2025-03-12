package com.example.pharmacy.admin.stock;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.stock.entity.StockLog;
import com.example.pharmacy.admin.stock.entity.dto.StockAdjustmentDTO;
import com.example.pharmacy.admin.stock.entity.vo.DrugStockVO;
import com.example.pharmacy.admin.stock.mapper.StockLogMapper;
import com.example.pharmacy.admin.stock.service.StockService;
import com.example.pharmacy.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StockTest {
    @InjectMocks
    private StockService stockService;

    @Mock
    private StockLogMapper stockLogMapper;

    @Test
    public void testGetAllStock() {
        List<DrugStockVO> mockStockList = Arrays.asList(
                new DrugStockVO(1L, "药品A", 50, LocalDateTime.now()),
                new DrugStockVO(2L, "药品B", 30, LocalDateTime.now())
        );

        Mockito.when(stockLogMapper.findAllStock()).thenReturn(mockStockList);

        List<DrugStockVO> stockList = stockService.getAllStock();
        Assertions.assertEquals(2, stockList.size());
        Assertions.assertEquals("药品A", stockList.get(0).getName());
    }

    @Test
    public void testGetStockById_Found() {
        DrugStockVO mockDrugStock = new DrugStockVO(1L, "药品A", 50, LocalDateTime.now());

        Mockito.when(stockLogMapper.findStockById(1L)).thenReturn(Optional.of(mockDrugStock));

        DrugStockVO result = stockService.getStockById(1L);
        Assertions.assertEquals("药品A", result.getName());
        Assertions.assertEquals(50, result.getStock());
    }

    @Test
    public void testGetStockById_NotFound() {
        Mockito.when(stockLogMapper.findStockById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomException.class, () -> stockService.getStockById(1L));
    }

    @Test
    public void testGetLowStockDrugs() {
        List<DrugStockVO> mockLowStockDrugs = Arrays.asList(
                new DrugStockVO(1L, "药品A", 5, LocalDateTime.now()),
                new DrugStockVO(2L, "药品B", 8, LocalDateTime.now())
        );

        Mockito.when(stockLogMapper.findLowStockDrugs(10)).thenReturn(mockLowStockDrugs);

        List<DrugStockVO> lowStockDrugs = stockService.getLowStockDrugs(10);
        Assertions.assertEquals(2, lowStockDrugs.size());
        Assertions.assertTrue(lowStockDrugs.stream().allMatch(drug -> drug.getStock() < 10));
    }

    @Test
    public void testAdjustStock_SuccessfulIncrease() {
        Drug mockDrug = new Drug(1L, "药品A", "分类1", "厂家A", "治疗感冒", 50.00, 50, 1, LocalDateTime.now(), LocalDateTime.now());

        StockAdjustmentDTO stockAdjustmentDTO = new StockAdjustmentDTO(1L, 1, 20);

        Mockito.when(stockLogMapper.findById(1L)).thenReturn(Optional.of(mockDrug));

        stockService.adjustStock(stockAdjustmentDTO);

        Mockito.verify(stockLogMapper, Mockito.times(1)).updateStock(1L, 70);
        Mockito.verify(stockLogMapper, Mockito.times(1)).insertStockLog(Mockito.any(StockLog.class));
    }

    @Test
    public void testAdjustStock_InsufficientStock() {
        Drug mockDrug = new Drug(1L, "药品A", "分类1", "厂家A", "治疗感冒", 10.00, 10, 1, LocalDateTime.now(), LocalDateTime.now());

        StockAdjustmentDTO stockAdjustmentDTO = new StockAdjustmentDTO(1L, 1, -20);

        Mockito.when(stockLogMapper.findById(1L)).thenReturn(Optional.of(mockDrug));

        Assertions.assertThrows(CustomException.class, () -> stockService.adjustStock(stockAdjustmentDTO));

        Mockito.verify(stockLogMapper, Mockito.never()).updateStock(Mockito.anyLong(), Mockito.anyInt());
        Mockito.verify(stockLogMapper, Mockito.never()).insertStockLog(Mockito.any(StockLog.class));
    }
}
