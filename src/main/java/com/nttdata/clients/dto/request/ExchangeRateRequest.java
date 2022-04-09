package com.nttdata.clients.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Client object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateRequest {
  @NotBlank(message = "Field buy must be required")
  private double buy;
  @NotBlank(message = "Field sell must be required")
  private double sell;
  @NotBlank(message = "Field message must be required")
  private Date date;
}
