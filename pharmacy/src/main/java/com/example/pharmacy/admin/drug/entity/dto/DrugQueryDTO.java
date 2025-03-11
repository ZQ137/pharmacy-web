package com.example.pharmacy.admin.drug.entity.dto;

import com.example.pharmacy.common.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.security.Timestamp;

@Data
@Schema(description = "药品查询 DTO")
public class DrugQueryDTO extends QueryDTO {

    @Schema(description = "药品名称",example = "三九胃泰颗粒")
    private String name;

    @Schema(description = "药品类别",example = "肠胃用药")
    private String category;

    @Schema(description = "生产商",example = "华润三九医药股份有限公司")
    private String manufacturer;

    @Schema(description = "是否可使用医保",example = "true")
    private Integer insuranceCovered;

}
