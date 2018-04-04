package hr.from.ivantoplak.springmvcrest.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @ApiModelProperty(value = "ID of a category", required = true)
    private Long id;

    @ApiModelProperty(value = "Name of a category", required = true)
    private String name;
}
