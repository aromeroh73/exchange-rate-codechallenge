package com.nttdata.clients.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.clients.dto.request.ExchangeRateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Client object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRate {
  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private String documentId;
  private double sell;
  private double buy;
  private Date date;
  private boolean active;

  /**
   * Return client from an ClientRequest.
   *
   * @param request ClientRequest object
   */
  public ExchangeRate(ExchangeRateRequest request) {
    sell = request.getSell();
    buy = request.getBuy();
    date = request.getDate();
    active = true;
  }
}
