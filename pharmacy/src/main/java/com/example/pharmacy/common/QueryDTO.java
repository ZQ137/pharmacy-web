package com.example.pharmacy.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "通用查询DTO")
public class QueryDTO {

    @Schema(description = "页码（默认1）")
    private Integer page = 1;

    @Schema(description = "每页条数（默认10）")
    private Integer size = 10;
}
