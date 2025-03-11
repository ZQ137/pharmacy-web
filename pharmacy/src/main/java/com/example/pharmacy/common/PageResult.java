package com.example.pharmacy.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页结果集")
public class PageResult<T> {

    @Schema(description = "总条数")
    private long total;

    @Schema(description = "总页数")
    private int totalPages;

    @Schema(description = "具体数据")
    private List<T> list;

}
