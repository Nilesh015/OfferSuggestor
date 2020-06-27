/* ----------------------------------------------------------------------------------------------------------------------
* © Copyright 2018 Visa. All Rights Reserved.
*
* NOTICE: The software and accompanying information and documentation (together, the “Software”) remain the property of
* and are proprietary to Visa and its suppliers and affiliates. The Software remains protected by intellectual property
* rights and may be covered by U.S. and foreign patents or patent applications. The Software is licensed and not sold.
*
* By accessing the Software you are agreeing to Visa's terms of use (developer.visa.com/terms) and privacy policy
* (developer.visa.com/privacy). In addition, all permissible uses of the Software must be in support of Visa products,
* programs and services provided through the Visa Developer Program (VDP) platform only (developer.visa.com).
* THE SOFTWARE AND ANY ASSOCIATED INFORMATION OR DOCUMENTATION IS PROVIDED ON AN “AS IS,” “AS AVAILABLE,” “WITH ALL
* FAULTS” BASIS WITHOUT WARRANTY OR CONDITION OF ANY KIND. YOUR USE IS AT YOUR OWN RISK.
---------------------------------------------------------------------------------------------------------------------- */

/*
 * Offers Data API
 * The Offers Data API provides developers a quick and easy way to retrieve offer information from VMORC. The API allows you to retrieve all your available offers or retrieve specific offers. In an offer-specific request, you may choose to filter your accessible offers by certain offer attributes or you may request for offers by its identifiers.
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.visa.developer.sample.offers_data_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * BIN
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2018-12-19T10:49:33.222+05:30[Asia/Kolkata]")
public class BIN {

  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("ubid")
  private Integer ubid = null;
  
  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("value")
  private String value = null;
  
  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("companyName")
  private String companyName = null;
  
  public BIN ubid(Integer ubid) {
    this.ubid = ubid;
    return this;
  }

  
  /**
  * the companyName&#x27;s UBID
  * @return ubid
  **/
  @ApiModelProperty(value = "the companyName's UBID")
  public Integer getUbid() {
    return ubid;
  }
  public void setUbid(Integer ubid) {
    this.ubid = ubid;
  }
  
  public BIN value(String value) {
    this.value = value;
    return this;
  }

  
  /**
  * the BIN numerical value. Max length: 20 characters
  * @return value
  **/
  @ApiModelProperty(value = "the BIN numerical value. Max length: 20 characters")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  
  public BIN companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  
  /**
  * the name of the bank associated to the BIN. Max length: 100 characters
  * @return companyName
  **/
  @ApiModelProperty(value = "the name of the bank associated to the BIN. Max length: 100 characters")
  public String getCompanyName() {
    return companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BIN BIN = (BIN) o;
    return Objects.equals(this.ubid, BIN.ubid) &&
        Objects.equals(this.value, BIN.value) &&
        Objects.equals(this.companyName, BIN.companyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ubid, value, companyName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BIN {\n");

    sb.append("    ubid: ").append(toIndentedString(ubid)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  
}


