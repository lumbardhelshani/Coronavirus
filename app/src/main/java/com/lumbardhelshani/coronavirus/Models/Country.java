package com.lumbardhelshani.coronavirus.Models;
/* user: lumba
   date: 5/8/2020
   time: 21:31
*/

public class Country {
    private String flag,countryName,casesTxt,todayCasesTxt,deathsTxt,todayDeathsTxt,recoveredTxt,activeTxt,criticalTxt;

    public Country() {
    }

    public Country(String flag, String countryName, String casesTxt, String todayCasesTxt, String deathsTxt, String todayDeathsTxt, String recoveredTxt, String activeTxt, String criticalTxt) {
        this.flag = flag;
        this.countryName = countryName;
        this.casesTxt = casesTxt;
        this.todayCasesTxt = todayCasesTxt;
        this.deathsTxt = deathsTxt;
        this.todayDeathsTxt = todayDeathsTxt;
        this.recoveredTxt = recoveredTxt;
        this.activeTxt = activeTxt;
        this.criticalTxt = criticalTxt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return countryName;
    }

    public void setCountry(String countryName) {
        this.countryName = countryName;
    }

    public String getCases() {
        return casesTxt;
    }

    public void setCases(String cases) {
        this.casesTxt = casesTxt;
    }

    public String getTodayCases() {
        return todayCasesTxt;
    }

    public void setTodayCases(String todayCasesTxt) {
        this.todayCasesTxt = todayCasesTxt;
    }

    public String getDeaths() {
        return deathsTxt;
    }

    public void setDeaths(String deathsTxt) {
        this.deathsTxt = deathsTxt;
    }

    public String getTodayDeaths() {
        return todayDeathsTxt;
    }

    public void setTodayDeaths(String todayDeathsTxt) {
        this.todayDeathsTxt = todayDeathsTxt;
    }

    public String getRecovered() {
        return recoveredTxt;
    }

    public void setRecovered(String recoveredTxt) {
        this.recoveredTxt = recoveredTxt;
    }

    public String getActive() {
        return activeTxt;
    }

    public void setActive(String activeTxt) {
        this.activeTxt = activeTxt;
    }

    public String getCritical() {
        return criticalTxt;
    }

    public void setCritical(String criticalTxt) {
        this.criticalTxt = criticalTxt;
    }
}
