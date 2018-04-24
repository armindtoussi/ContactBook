package ca.bc.northvan.armintoussi.contactbook.Models;

import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Address {

    private int     addrID;
    private String  addrStreetAddress;
    private String  addrCity;
    private String  addrState;
    private String  addrCountry;
    private String  addrPostCode;

    private Address(Builder builder) {
        this.addrStreetAddress = builder.addrStreetAddress;
        this.addrCity          = builder.addrCity;
        this.addrState         = builder.addrState;
        this.addrCountry       = builder.addrCountry;
        this.addrPostCode      = builder.addrPostCode;
    }

    public static class Builder {
        private String  addrStreetAddress;
        private String  addrCity;
        private String  addrState;
        private String  addrCountry;
        private String  addrPostCode;

        public Builder addrStreetAddress(final String addrStreetAddress) {
            this.addrStreetAddress = addrStreetAddress;
            return this;
        }

        public Builder addrCity(final String addrCity) {
            this.addrCity = addrCity;
            return this;
        }

        public Builder addrState(final String addrState) {
            this.addrState = addrState;
            return this;
        }

        public Builder addrCountry(final String addrCountry) {
            this.addrCountry = addrCountry;
            return this;
        }

        public Builder addrPostCode(final String addrPostCode) {
            this.addrPostCode = addrPostCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    public void setAddrStreetAddress(final String addrStreetName) {
        if(addrStreetName != null && addrStreetName.length() > 2) {
            this.addrStreetAddress = addrStreetName;
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

    public String getAddrStreetAddress() {
        return addrStreetAddress;
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
        return new Builder().addrStreetAddress(this.addrStreetAddress)
                              .addrCity(this.addrCity)
                              .addrState(this.addrState)
                              .addrCountry(this.addrCountry)
                              .addrPostCode(this.addrPostCode)
                              .build();
    }

    /**
     * Address builder class, gives us a ability to statically call
     * method for a address to be built.
     */
    public static final class AddressBuilder {

        /**
         * Creates an address using the Address Builder.
         *
         * @param stAddress the street address.
         * @param city the city.
         * @param region the state/province or region.
         * @param country the country.
         * @param postCode the postal code.
         *
         * @return a built address.
         */
        public static Address createAddress(final String stAddress,
                                            final String city,
                                            final String region,
                                            final String country,
                                            final String postCode) {
            Address.Builder ab = new Address.Builder();

            if (Utilities.checkNotNullNotEmpty(stAddress)) {
                ab.addrStreetAddress(stAddress);
            }
            if (Utilities.checkNotNullNotEmpty(city)) {
                ab.addrCity(city);
            }
            if (Utilities.checkNotNullNotEmpty(region)) {
                ab.addrState(region);
            }
            if(Utilities.checkNotNullNotEmpty(country)) {
                ab.addrCountry(country);
            }
            if(Utilities.checkNotNullNotEmpty(postCode)) {
                ab.addrPostCode(postCode);
            }
            return ab.build();
        }
    }
}
