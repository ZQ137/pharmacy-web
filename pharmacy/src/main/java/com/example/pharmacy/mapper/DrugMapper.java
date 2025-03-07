package com.example.pharmacy.mapper;

import com.example.pharmacy.entity.Drug;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface DrugMapper {

    // 分页查询药品（带搜索功能）
    List<Drug> findAll(@Param("search") String search, @Param("offset") int offset, @Param("size") int size);

    // 统计符合条件的药品总数
    int count(@Param("search") String search);

    // 通过 ID 查询药品
    @Select("SELECT * FROM drug WHERE id = #{id}")
    Drug findById(@Param("id") Integer id);

    // 插入药品
    void insert(Drug drug);

    // 更新药品
    void update(Drug drug);

    // 删除药品
    @Delete("DELETE FROM drug WHERE id = #{id}")
    void delete(@Param("id") Integer id);
}
