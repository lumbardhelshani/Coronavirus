package com.lumbardhelshani.coronavirus.Models;
/* user: lumba
   date: 5/11/2020
   time: 15:35
*/

public class WorldCovidData {
    private String updated;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long active;
    private long critical;
    private double casesPerOneMillion;
    private double deathsPerOneMillion;
    private long tests;
    private double testsPerOneMillion;
    private long affectedCountries;

    public String getUpdated() { return updated; }
    public void setUpdated(String value) { this.updated = value; }

    public long getCases() { return cases; }
    public void setCases(long value) { this.cases = value; }

    public long getTodayCases() { return todayCases; }
    public void setTodayCases(long value) { this.todayCases = value; }

    public long getDeaths() { return deaths; }
    public void setDeaths(long value) { this.deaths = value; }

    public long getTodayDeaths() { return todayDeaths; }
    public void setTodayDeaths(long value) { this.todayDeaths = value; }

    public long getRecovered() { return recovered; }
    public void setRecovered(long value) { this.recovered = value; }

    public long getActive() { return active; }
    public void setActive(long value) { this.active = value; }

    public long getCritical() { return critical; }
    public void setCritical(long value) { this.critical = value; }

    public double getCasesPerOneMillion() { return casesPerOneMillion; }
    public void setCasesPerOneMillion(double value) { this.casesPerOneMillion = value; }

    public double getDeathsPerOneMillion() { return deathsPerOneMillion; }
    public void setDeathsPerOneMillion(double value) { this.deathsPerOneMillion = value; }

    public long getTests() { return tests; }
    public void setTests(long value) { this.tests = value; }

    public double getTestsPerOneMillion() { return testsPerOneMillion; }
    public void setTestsPerOneMillion(double value) { this.testsPerOneMillion = value; }

    public long getAffectedCountries() { return affectedCountries; }
    public void setAffectedCountries(long value) { this.affectedCountries = value; }
}
