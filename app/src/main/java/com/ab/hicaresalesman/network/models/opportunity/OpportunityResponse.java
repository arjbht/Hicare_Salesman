package com.ab.hicaresalesman.network.models.opportunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arjun Bhatt on 4/22/2021.
 */
public class OpportunityResponse {
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("Data")
    @Expose
    private List<OpportunityData> data;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<OpportunityData> getData() {
        return data;
    }

    public void setData(List<OpportunityData> data) {
        this.data = data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
