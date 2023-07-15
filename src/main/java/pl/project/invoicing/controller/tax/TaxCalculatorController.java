package pl.project.invoicing.controller.tax;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.project.invoicing.service.TaxCalculatorResult;
import pl.project.invoicing.service.TaxCalculatorService;

@RestController
@AllArgsConstructor
public class TaxCalculatorController implements TaxCalculatorApi {

  private final TaxCalculatorService taxService;

  @Override
  public TaxCalculatorResult calculateTaxes(String taxIdentificationNumber) {
    return taxService.calculateTaxes(taxIdentificationNumber);
  }
}
