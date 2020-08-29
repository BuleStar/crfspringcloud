package com.crfspringcloud.consumerfeginhystrixstream.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author hongfei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Long id;
  private String username;
  private String name;
  private Integer age;
  private BigDecimal balance;

}
