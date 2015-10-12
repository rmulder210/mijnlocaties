package com.goldengateway.apps.mijnlocaties.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time model obj
 */
public class Time {

    @Expose
    @SerializedName("product_id")
    private String productId;

    @Expose
    @SerializedName("display_name")
    private String displayName;

    @Expose
    @SerializedName("estimate")
    private int estimate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }
}