package ca.bc.northvan.armintoussi.contactbook.Models;

import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

/**
 * Created by armin2 on 3/25/2018.
 *
 * This class represents an Address.
 * Builder pattern is implemented to build
 * a address as well as AddressBuilder class
 * for ease of repeatedly building an address.
 */
public class Address {
    /** String that holds street address. */
    private String  addrStreetAddress;
    /** String that holds city. */
    private String  addrCity;
    /** String that holds city. */
    private String  addrState;
    /** String that holds state/region/prov. */
    private String  addrCountry;
    /** String that holds post code. */
    private String  addrPostCode;

    /**
     * Private ctor that sets all the instance variables.
     *
     * @param builder builder obj used for building this obj.
     */
    private Address(Builder builder) {
        this.addrStreetAddress = builder.addrStreetAddress;
        this.addrCity          = builder.addrCity;
        this.addrState         = builder.addrState;
        this.addrCountry       = builder.addrCountry;
        this.addrPostCode      = builder.addrPostCode;
    }

    /**
     * Static Builder class for easily building an
     * Address with variable arguments.
     */
    public static class Builder {
        /** String that holds street address. */
        private String  addrStreetAddress;
        /** String that holds city. */
        private String  addrCity;
        /** String that holds city. */
        private String  addrState;
        /** String that holds state/region/prov. */
        private String  addrCountry;
        /** String that holds post code. */
        private String  addrPostCode;

        /**
         * Add the street address.
         *
         * @param addrStreetAddress the address to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder addrStreetAddress(final String addrStreetAddress) {
            this.addrStreetAddress = addrStreetAddress;
            return this;
        }

        /**
         * Add the city.
         *
         * @param addrCity the city to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder addrCity(final String addrCity) {
            this.addrCity = addrCity;
            return this;
        }

        /**
         * Add the state/region/prov.
         *
         * @param addrState the state/region to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder addrState(final String addrState) {
            this.addrState = addrState;
            return this;
        }

        /**
         * Add the country.
         *
         * @param addrCountry the country to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder addrCountry(final String addrCountry) {
            this.addrCountry = addrCountry;
            return this;
        }

        /**
         * Add the post code.
         *
         * @param addrPostCode the post code to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder addrPostCode(final String addrPostCode) {
            this.addrPostCode = addrPostCode;
            return this;
        }

        /**
         * Build method to finis hte build and return a
         * built Address obj.
         *
         * @return build Address obj.
         */
        public Address build() {
            return new Address(this);
        }
    }

    /**
     * Sets the street address.
     *
     * @param addrStreetName street address as a String.
     */
    public void setAddrStreetAddress(final String addrStreetName) {
        if(addrStreetName != null && addrStreetName.length() > 2) {
            this.addrStreetAddress = addrStreetName;
        }
    }

    /**
     * Sets the city.
     *
     * @param addrCity city as a String.
     */
    public void setAddrCity(final String addrCity) {
        if(addrCity != null && addrCity.length() > 1) {
            this.addrCity = addrCity;
        }
    }

    /**
     * Sets the state/region.
     *
     * @param addrState state/region as a String.
     */
    public void setAddrState(final String addrState) {
        if(addrState != null && addrState.length() > 1) {
            this.addrState = addrState;
        }
    }

    /**
     * Sets the country.
     *
     * @param addrCountry country as a String.
     */
    public void setAddrCountry(final String addrCountry) {
        if(addrCountry != null && addrCountry.length() > 2) {
            this.addrCountry = addrCountry;
        }
    }

    /**
     * Sets the post code.
     *
     * @param addrPostCode post code as a String.
     */
    public void setAddrPostCode(final String addrPostCode) {
        if(addrPostCode != null && addrPostCode.length() > 2) {
            this.addrPostCode = addrPostCode;
        }
    }

    /**
     * Gets the street address.
     *
     * @return street address as a String.
     */
    public String getAddrStreetAddress() {
        return addrStreetAddress;
    }

    /**
     * Gets the city.
     *
     * @return city as a String.
     */
    public String getAddrCity() {
        return addrCity;
    }

    /**
     * Gets the state/region.
     *
     * @return state/region as a String.
     */
    public String getAddrState() {
        return addrState;
    }

    /**
     * Gets the country.
     *
     * @return country as a String.
     */
    public String getAddrCountry() {
        return addrCountry;
    }

    /**
     * Gets the post code.
     *
     * @return post code as a String.
     */
    public String getAddrPostCode() {
        return addrPostCode;
    }

    /**
     * Makes a clone of the Address object this is called on.
     *
     * @return a built clone of the obj.
     */
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
