package com.ab.hicaresalesman.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ab.hicaresalesman.network.models.activity.ActivityData;

/**
 * Created by Arjun Bhatt on 4/23/2021.
 */
public class ActivityViewModel implements Parcelable {
    private int activityId;
    private String opportunityId;
    private String activityCode;
    private String activityName;
    private boolean isDeleted;
    private String createdOn;
    private String modifiedOn;
    private int indistryId;
    private String industryName;
    private Boolean isCostGenerated;

    public ActivityViewModel() {
        this.activityId = 0;
        this.opportunityId = "NA";
        this.activityCode = "NA";
        this.activityName = "NA";
        this.isDeleted = false;
        this.createdOn = "NA";
        this.modifiedOn = "NA";
        this.indistryId = 0;
        this.industryName = "NA";
        this.isCostGenerated = false;
    }


    protected ActivityViewModel(Parcel in) {
        activityId = in.readInt();
        opportunityId = in.readString();
        activityCode = in.readString();
        activityName = in.readString();
        isDeleted = in.readByte() != 0;
        createdOn = in.readString();
        modifiedOn = in.readString();
        indistryId = in.readInt();
        industryName = in.readString();
        byte tmpIsCostGenerated = in.readByte();
        isCostGenerated = tmpIsCostGenerated == 0 ? null : tmpIsCostGenerated == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activityId);
        dest.writeString(opportunityId);
        dest.writeString(activityCode);
        dest.writeString(activityName);
        dest.writeByte((byte) (isDeleted ? 1 : 0));
        dest.writeString(createdOn);
        dest.writeString(modifiedOn);
        dest.writeInt(indistryId);
        dest.writeString(industryName);
        dest.writeByte((byte) (isCostGenerated == null ? 0 : isCostGenerated ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivityViewModel> CREATOR = new Creator<ActivityViewModel>() {
        @Override
        public ActivityViewModel createFromParcel(Parcel in) {
            return new ActivityViewModel(in);
        }

        @Override
        public ActivityViewModel[] newArray(int size) {
            return new ActivityViewModel[size];
        }
    };

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getIndistryId() {
        return indistryId;
    }

    public void setIndistryId(int indistryId) {
        this.indistryId = indistryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Boolean getCostGenerated() {
        return isCostGenerated;
    }

    public void setCostGenerated(Boolean costGenerated) {
        isCostGenerated = costGenerated;
    }

    public void clone(ActivityData data){
        this.activityId = data.getActivityId();
        this.activityName = data.getActivityName();
        this.opportunityId = data.getOpportunityId();
        this.activityCode = data.getActivityCode();
        this.isDeleted = data.getIsDeleted();
        this.createdOn = data.getCreatedOn();
        this.modifiedOn = data.getModifiedOn();
        this.indistryId = data.getIndustryId();
        this.industryName = data.getIndustryName();
        this.isCostGenerated = data.getCost_Generated();
    }
}
