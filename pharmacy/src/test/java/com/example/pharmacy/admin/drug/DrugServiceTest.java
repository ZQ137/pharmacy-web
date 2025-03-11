package com.example.pharmacy.admin.drug;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.drug.entity.dto.DrugAddDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugQueryDTO;
import com.example.pharmacy.admin.drug.entity.dto.DrugUpdateDTO;
import com.example.pharmacy.admin.drug.entity.vo.DrugVO;
import com.example.pharmacy.admin.drug.mapper.DrugMapper;
import com.example.pharmacy.admin.drug.service.DrugService;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DrugServiceTest {
    @InjectMocks
    private DrugService drugService;

    @Mock
    private DrugMapper drugMapper;

    @Test
    void testGetDrugsByConditions() {
        // 模拟数据
        DrugQueryDTO queryDTO = new DrugQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setSize(10);

        List<Drug> drugs = new ArrayList<>();
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("Paracetamol");
        drugs.add(drug);

        Mockito.when(drugMapper.findDrugsByCondition(queryDTO)).thenReturn(drugs);

        // 调用方法
        PageResult<DrugVO> result = drugService.getDrugsByConditions(queryDTO);

        // 断言
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getList().size());
        assertEquals("Paracetamol", result.getList().get(0).getName());
    }

    @Test
    void testGetDrugByIdFound() {
        // 模拟返回数据
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("Ibuprofen");

        Mockito.when(drugMapper.findById(1L)).thenReturn(drug);

        // 调用方法
        Optional<Drug> result = drugService.getDrugById(1L);

        // 断言
        assertTrue(result.isPresent());
        assertEquals("Ibuprofen", result.get().getName());
    }

    @Test
    void testGetDrugByIdNotFound() {
        Mockito.when(drugMapper.findById(2L)).thenReturn(null);

        Optional<Drug> result = drugService.getDrugById(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveDrugThrowsExceptionIfExists() {
        DrugAddDTO drugAddDTO = new DrugAddDTO();
        drugAddDTO.setName("Aspirin");

        // 模拟抛出异常
        Assertions.assertThrows(CustomException.class, () -> drugService.saveDrug(drugAddDTO));
    }

    @Test
    void testDeleteDrugSuccess() {
        // 模拟数据库中存在该药品
        Drug drug = new Drug();
        drug.setId(1L);
        Mockito.when(drugMapper.findById(1L)).thenReturn(drug);

        // 调用删除方法
        assertDoesNotThrow(() -> drugService.deleteDrug(1L));

        // 验证是否调用了 delete 方法
        Mockito.verify(drugMapper, Mockito.times(1)).delete(1L);
    }

    @Test
    void testDeleteDrugThrowsExceptionIfNotFound() {
        Mockito.when(drugMapper.findById(2L)).thenReturn(null);

        Assertions.assertThrows(CustomException.class, () -> drugService.deleteDrug(2L));
    }

    @Test
    void testUpdateDrugSuccess() {
        DrugUpdateDTO updateDTO = new DrugUpdateDTO();
        updateDTO.setId(1L);
        updateDTO.setName("Updated Drug");

        // 模拟数据库中存在
        Drug existingDrug = new Drug();
        existingDrug.setId(1L);
        Mockito.when(drugMapper.findById(1L)).thenReturn(existingDrug);

        // 调用更新方法
        assertDoesNotThrow(() -> drugService.updateDrug(updateDTO));

        // 验证 update 方法是否被调用
        Mockito.verify(drugMapper, Mockito.times(1)).update(Mockito.any(Drug.class));
    }

    @Test
    void testUpdateDrugThrowsExceptionIfNotFound() {
        DrugUpdateDTO updateDTO = new DrugUpdateDTO();
        updateDTO.setId(2L);
        updateDTO.setName("Non-Existent Drug");

        Mockito.when(drugMapper.findById(2L)).thenReturn(null);

        Assertions.assertThrows(CustomException.class, () -> drugService.updateDrug(updateDTO));
    }
}
