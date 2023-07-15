package pl.project.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceEntry {

  @ApiModelProperty(value = "Product/service description", required = true, example = "GIGABYTE GeForce RTX 3070 Eagle OC LHR 8GB")
  private String description;

  @ApiModelProperty(value = "Number of items", required = true, example = "62")
  private int quantity;

  @ApiModelProperty(value = "Product/service net price", required = true, example = "2211.23")
  private BigDecimal price;

  @ApiModelProperty(value = "Product/service tax value", required = true, example = "176.9")
  private BigDecimal vatValue;

  @ApiModelProperty(value = "Tax rate", required = true)
  private Vat vatRate;

}
