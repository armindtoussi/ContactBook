package ca.bc.northvan.armintoussi.contactbook.Models;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Address {

    private int     addrStreetNumber;
    private int     addrAptNumber;
    private String  addrStreetName;
    private String  addrCity;
    private String  addrState;
    private String  addrCountry;
    private String  addrPostCode;
    private boolean isApt;



    public Address(final int addrStreetNumber,
                   final int addrAptNumber,
                   final String addrStreetName,
                   final String addrCity,
                   final String addrState,
                   final String addrCountry,
                   final String addrPostCode) {

        this.isApt = true;
        this.setAddrStreetNumber(addrStreetNumber);
        this.setAddrAptNumber(addrAptNumber);
        this.setAddrStreetName(addrStreetName);
        this.setAddrCity(addrCity);
        this.setAddrState(addrState);
        this.setAddrCountry(addrCountry);
        this.setAddrPostCode(addrPostCode);
    }

    public Address(final int addrStreetNumber,
                   final String addrStreetName,
                   final String addrCity,
                   final String addrState,
                   final String addrCountry,
                   final String addrPostCode) {

        this.isApt = false;
        this.setAddrStreetNumber(addrStreetNumber);
        this.setAddrStreetName(addrStreetName);
        this.setAddrCity(addrCity);
        this.setAddrState(addrState);
        this.setAddrCountry(addrCountry);
        this.setAddrPostCode(addrPostCode);
    }

    public void setAddrStreetNumber(final int addrStreetNumber) {
        if(addrStreetNumber > 0) {
            this.addrStreetNumber = addrStreetNumber;
        }
    }

    public void setAddrAptNumber(final int addrAptNumber) {
        if(addrAptNumber > 0) {
            this.addrAptNumber = addrAptNumber;
        }
    }

    public void setAddrStreetName(final String addrStreetName) {
        if(addrStreetName != null && addrStreetName.length() > 2) {
            this.addrStreetName = addrStreetName;
        }
    }

    public void setAddrCity(final String addrCity) {
        if(addrCity != null && addrCity.length() > 1) {
            this.addrCity = addrCity;
        }
    }

    public void setAddrState(final String addrState) {
        if(addrState != null && addrState.length() > 1) {
            this.addrState = addrState;
        }
    }

    public void setAddrCountry(final String addrCountry) {
        if(addrCountry != null && addrCountry.length() > 2) {
            this.addrCountry = addrCountry;
        }
    }

    public void setAddrPostCode(final String addrPostCode) {
        if(addrPostCode != null && addrPostCode.length() > 2) {
            this.addrPostCode = addrPostCode;
        }
    }

    public int getAddrStreetNumber() {
        return addrStreetNumber;
    }

    public int getAddrAptNumber() {
        return addrAptNumber;
    }

    public String getAddrStreetName() {
        return addrStreetName;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public String getAddrState() {
        return addrState;
    }

    public String getAddrCountry() {
        return addrCountry;
    }

    public String getAddrPostCode() {
        return addrPostCode;
    }

    public Object clone() {
        Address aClone;
        if(isApt) {
            aClone = new Address(this.addrStreetNumber, this.addrAptNumber, this.addrStreetName,
                                 this.addrCity, this.addrState, this.addrCountry, this.addrPostCode);
        } else {
            aClone = new Address(this.addrStreetNumber, this.addrStreetName,
                                 this.addrCity, this.addrState, this.addrCountry, this.addrPostCode);
        }
        return aClone;
    }
}
