package com.lumbardhelshani.coronavirus.Models;
/* user: lumba
   date: 5/11/2020
   time: 16:03
*/

import java.util.*;
import java.io.IOException;

public class CountryCovidData {
    private long updated;
    private String country;
    private CountryInfo countryInfo;
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
    private long testsPerOneMillion;
    private Continent continent;

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long value) {
        this.updated = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo value) {
        this.countryInfo = value;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long value) {
        this.cases = value;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(long value) {
        this.todayCases = value;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long value) {
        this.deaths = value;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(long value) {
        this.todayDeaths = value;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long value) {
        this.recovered = value;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long value) {
        this.active = value;
    }

    public long getCritical() {
        return critical;
    }

    public void setCritical(long value) {
        this.critical = value;
    }

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(double value) {
        this.casesPerOneMillion = value;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(double value) {
        this.deathsPerOneMillion = value;
    }

    public long getTests() {
        return tests;
    }

    public void setTests(long value) {
        this.tests = value;
    }

    public long getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(long value) {
        this.testsPerOneMillion = value;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent value) {
        this.continent = value;
    }


// Continent.java

    public enum Continent {
        AFRICA, ASIA, AUSTRALIA_OCEANIA, EMPTY, EUROPE, NORTH_AMERICA, SOUTH_AMERICA;

        public String toValue() {
            switch (this) {
                case AFRICA:
                    return "Africa";
                case ASIA:
                    return "Asia";
                case AUSTRALIA_OCEANIA:
                    return "Australia/Oceania";
                case EMPTY:
                    return "";
                case EUROPE:
                    return "Europe";
                case NORTH_AMERICA:
                    return "North America";
                case SOUTH_AMERICA:
                    return "South America";
            }
            return null;
        }

        public static Continent forValue(String value) throws IOException {
            if (value.equals("Africa")) return AFRICA;
            if (value.equals("Asia")) return ASIA;
            if (value.equals("Australia/Oceania")) return AUSTRALIA_OCEANIA;
            if (value.equals("")) return EMPTY;
            if (value.equals("Europe")) return EUROPE;
            if (value.equals("North America")) return NORTH_AMERICA;
            if (value.equals("South America")) return SOUTH_AMERICA;
            throw new IOException("Cannot deserialize Continent");
        }
    }

// CountryInfo.java

    public class CountryInfo {
        private Long id;
        private String iso2;
        private String iso3;
        private double lat;
        private double countryInfoLong;
        private String flag;

        public Long getID() {
            return id;
        }

        public void setID(Long value) {
            this.id = value;
        }

        public String getIso2() {
            return iso2;
        }

        public void setIso2(String value) {
            this.iso2 = value;
        }

        public String getIso3() {
            return iso3;
        }

        public void setIso3(String value) {
            this.iso3 = value;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double value) {
            this.lat = value;
        }

        public double getCountryInfoLong() {
            return countryInfoLong;
        }

        public void setCountryInfoLong(double value) {
            this.countryInfoLong = value;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String value) {
            this.flag = value;
        }
    }
}
