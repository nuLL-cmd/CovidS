
package com.automatodev.covids.model.entity;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Covid {

    @SerializedName("active")
    private Long mActive;
    @SerializedName("cases")
    private Long mCases;
    @SerializedName("casesPerOneMillion")
    private Long mCasesPerOneMillion;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("critical")
    private Long mCritical;
    @SerializedName("deaths")
    private Long mDeaths;
    @SerializedName("deathsPerOneMillion")
    private Long mDeathsPerOneMillion;
    @SerializedName("recovered")
    private Long mRecovered;
    @SerializedName("testsPerOneMillion")
    private Long mTestsPerOneMillion;
    @SerializedName("todayCases")
    private Long mTodayCases;
    @SerializedName("todayDeaths")
    private Long mTodayDeaths;
    @SerializedName("totalTests")
    private Long mTotalTests;

    public Long getActive() {
        return mActive;
    }

    public void setActive(Long active) {
        mActive = active;
    }

    public Long getCases() {
        return mCases;
    }

    public void setCases(Long cases) {
        mCases = cases;
    }

    public Long getCasesPerOneMillion() {
        return mCasesPerOneMillion;
    }

    public void setCasesPerOneMillion(Long casesPerOneMillion) {
        mCasesPerOneMillion = casesPerOneMillion;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Long getCritical() {
        return mCritical;
    }

    public void setCritical(Long critical) {
        mCritical = critical;
    }

    public Long getDeaths() {
        return mDeaths;
    }

    public void setDeaths(Long deaths) {
        mDeaths = deaths;
    }

    public Long getDeathsPerOneMillion() {
        return mDeathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Long deathsPerOneMillion) {
        mDeathsPerOneMillion = deathsPerOneMillion;
    }

    public Long getRecovered() {
        return mRecovered;
    }

    public void setRecovered(Long recovered) {
        mRecovered = recovered;
    }

    public Long getTestsPerOneMillion() {
        return mTestsPerOneMillion;
    }

    public void setTestsPerOneMillion(Long testsPerOneMillion) {
        mTestsPerOneMillion = testsPerOneMillion;
    }

    public Long getTodayCases() {
        return mTodayCases;
    }

    public void setTodayCases(Long todayCases) {
        mTodayCases = todayCases;
    }

    public Long getTodayDeaths() {
        return mTodayDeaths;
    }

    public void setTodayDeaths(Long todayDeaths) {
        mTodayDeaths = todayDeaths;
    }

    public Long getTotalTests() {
        return mTotalTests;
    }

    public void setTotalTests(Long totalTests) {
        mTotalTests = totalTests;
    }

}
