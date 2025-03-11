package com.example.pharmacy.admin.drug.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "药品添加 DTO")
public class DrugUpdateDTO {

    @Schema(description = "药品ID",example = "1")
    private Long id;

    @Schema(description = "药品名称",example = "三九胃泰颗粒")
    @NotBlank(message = "药品名称不能为空")
    private String name;

    @Schema(description = "药品类别",example = "肠胃用药")
    @NotBlank(message = "药品类别不能为空")
    private String category;

    @Schema(description = "生产商",example = "华润三九医药股份有限公司")
    private String manufacturer;

    @Schema(description = "药效描述",example = "清热燥湿，行气活血，柔肝止痛。用于湿热内蕴、气滞血瘀所致的胃痛，症见脘腹隐痛、饱胀反酸、恶心呕吐、嘈杂纳减；浅表性胃炎见上述证候者。")
    private String efficacy;

    @Schema(description = "价格",example = "9.9")
    private Double price;

    @Schema(description = "库存",example = "101")
    private Integer stock;

    @Schema(description = "是否可使用医保",example = "true")
    private Integer insuranceCovered;

}
