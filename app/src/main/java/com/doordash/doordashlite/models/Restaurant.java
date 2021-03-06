package com.doordash.doordashlite.models;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Restaurant {
    private static final String PREOFER = "pre-order";

    @SerializedName("business_id")
    public long businessId;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("status")
    public String status;

    @SerializedName("cover_img_url")
    public String coverImageUrl;

    @SerializedName("tags")
    public List<String> tags;

    @SerializedName("status_type")
    public String statusType;

    public boolean isLiked;

    public String getStatus() {
        if (this.statusType.equals(PREOFER)) {
            return PREOFER;
        } else {
            return this.status;
        }
    }
}
