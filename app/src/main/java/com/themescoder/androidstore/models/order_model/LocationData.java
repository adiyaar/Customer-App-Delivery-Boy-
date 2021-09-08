package com.themescoder.androidstore.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {

    @SerializedName("accuracy")
    @Expose
    private Double accuracy;
    @SerializedName("altitude")
    @Expose
    private Double altitude;
    @SerializedName("bearing")
    @Expose
    private Double bearing;
    @SerializedName("bearingAccuracyDegrees")
    @Expose
    private Double bearingAccuracyDegrees;
    @SerializedName("complete")
    @Expose
    private Boolean complete;
    @SerializedName("elapsedRealtimeNanos")
    @Expose
    private Double elapsedRealtimeNanos;
    @SerializedName("extras")
    @Expose
    private Extras extras;
    @SerializedName("fromMockProvider")
    @Expose
    private Boolean fromMockProvider;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("speedAccuracyMetersPerSecond")
    @Expose
    private Double speedAccuracyMetersPerSecond;
    @SerializedName("time")
    @Expose
    private Double time;
    @SerializedName("verticalAccuracyMeters")
    @Expose
    private Double verticalAccuracyMeters;

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public Double getBearingAccuracyDegrees() {
        return bearingAccuracyDegrees;
    }

    public void setBearingAccuracyDegrees(Double bearingAccuracyDegrees) {
        this.bearingAccuracyDegrees = bearingAccuracyDegrees;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Double getElapsedRealtimeNanos() {
        return elapsedRealtimeNanos;
    }

    public void setElapsedRealtimeNanos(Double elapsedRealtimeNanos) {
        this.elapsedRealtimeNanos = elapsedRealtimeNanos;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public Boolean getFromMockProvider() {
        return fromMockProvider;
    }

    public void setFromMockProvider(Boolean fromMockProvider) {
        this.fromMockProvider = fromMockProvider;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getSpeedAccuracyMetersPerSecond() {
        return speedAccuracyMetersPerSecond;
    }

    public void setSpeedAccuracyMetersPerSecond(Double speedAccuracyMetersPerSecond) {
        this.speedAccuracyMetersPerSecond = speedAccuracyMetersPerSecond;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getVerticalAccuracyMeters() {
        return verticalAccuracyMeters;
    }

    public void setVerticalAccuracyMeters(Double verticalAccuracyMeters) {
        this.verticalAccuracyMeters = verticalAccuracyMeters;
    }

}