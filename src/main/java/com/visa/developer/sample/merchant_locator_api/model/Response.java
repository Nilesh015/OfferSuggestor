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
 * Merchant Locator API
 * Find Visa accepting merchants around by geolocation
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.visa.developer.sample.merchant_locator_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Response
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2018-12-19T10:49:03.812+05:30[Asia/Kolkata]")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("matchScore")
  private String matchScore = null;
  
  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("matchIndicators")
  private MatchIndicators matchIndicators = null;
  
  
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("responseValues")
  private ResponseValues responseValues = null;
  
  public Response matchScore(String matchScore) {
    this.matchScore = matchScore;
    return this;
  }

  
  /**
  * MatchScore for the record
  * @return matchScore
  **/
  @ApiModelProperty(value = "MatchScore for the record")
  public String isMatchScore() {
    return matchScore;
  }
  public void setMatchScore(String matchScore) {
    this.matchScore = matchScore;
  }
  
  public Response matchIndicators(MatchIndicators matchIndicators) {
    this.matchIndicators = matchIndicators;
    return this;
  }

  
  /**
  * List of attributes that found a match or did not find a match
  * @return matchIndicators
  **/
  @ApiModelProperty(value = "List of attributes that found a match or did not find a match")
  public MatchIndicators isMatchIndicators() {
    return matchIndicators;
  }
  public void setMatchIndicators(MatchIndicators matchIndicators) {
    this.matchIndicators = matchIndicators;
  }
  
  public Response responseValues(ResponseValues responseValues) {
    this.responseValues = responseValues;
    return this;
  }

  
  /**
  * Get responseValues
  * @return responseValues
  **/
  @ApiModelProperty(value = "")
  public ResponseValues getResponseValues() {
    return responseValues;
  }
  public void setResponseValues(ResponseValues responseValues) {
    this.responseValues = responseValues;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Response response = (Response) o;
    return Objects.equals(this.matchScore, response.matchScore) &&
        Objects.equals(this.matchIndicators, response.matchIndicators) &&
        Objects.equals(this.responseValues, response.responseValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchScore, matchIndicators, responseValues);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Response {\n");

    sb.append("    matchScore: ").append(toIndentedString(matchScore)).append("\n");
    sb.append("    matchIndicators: ").append(toIndentedString(matchIndicators)).append("\n");
    sb.append("    responseValues: ").append(toIndentedString(responseValues)).append("\n");
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

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MatchIndicators {

    @JsonProperty("merchantCountryCode")
    private String merchantCountryCode;
    @JsonProperty("merchantName")
    private String merchantName;

    public String getMerchantCountryCode() {
      return merchantCountryCode;
    }

    public void setMerchantCountryCode(String merchantCountryCode) {
      this.merchantCountryCode = merchantCountryCode;
    }

    public String getMerchantName() {
      return merchantName;
    }

    public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
    }
  }

  
}



