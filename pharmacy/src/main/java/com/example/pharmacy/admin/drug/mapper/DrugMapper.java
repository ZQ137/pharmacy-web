package com.example.pharmacy.admin.drug.mapper;

import com.example.pharmacy.admin.drug.entity.Drug;
import com.example.pharmacy.admin.drug.entity.dto.DrugQueryDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface DrugMapper {

    /**
     * 根据 ID 查询药品
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM drug WHERE id = #{id}")
    Drug findById(@Param("id") Long id);

    /**
     * 插入药品
     *
     * @param drug
     */
    void insert(Drug drug);

    /**
     * 更新药品
     *
     * @param drug
     */
    void update(Drug drug);

    /**
     * 根据id删除药品
     *
     * @param id
     */
    @Delete("DELETE FROM drug WHERE id = #{id}")
    void delete(@Param("id") Long id);

    /**
     * 条件查询药品信息
     *
     * @param queryDTO
     * @return
     */
    List<Drug> findDrugsByCondition(@Param("queryDTO") DrugQueryDTO queryDTO);

}
