package cn.szzxol.pro.geoip.geoapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulates metadata about the GeoIP database. The database has a date, is a
 * premium or standard version, and is one of the following types:
 *
 * <ul>
 * <li>Country edition -- this is the most common version of the database. It
 * includes the name of the country and it's ISO country code given an IP
 * address.
 * <li>Region edition -- includes the country information as well as what U.S.
 * state or Canadian province the IP address is from if the IP address is from
 * the U.S. or Canada.
 * <li>City edition -- includes country, region, city, postal code, latitude,
 * and longitude information.
 * <li>Org edition -- includes netblock owner.
 * <li>ISP edition -- ISP information.
 * </ul>
 *
 * @see com.maxmind.geoip.LookupService#getDatabaseInfo()
 * @author Matt Tucker
 */
public class DatabaseInfo {

    public static final int COUNTRY_EDITION = 1;
    public static final int REGION_EDITION_REV0 = 7;
    public static final int REGION_EDITION_REV1 = 3;
    public static final int CITY_EDITION_REV0 = 6;
    public static final int CITY_EDITION_REV1 = 2;
    public static final int ORG_EDITION = 5;
    public static final int ISP_EDITION = 4;
    public static final int PROXY_EDITION = 8;
    public static final int ASNUM_EDITION = 9;
    public static final int NETSPEED_EDITION = 10;
    public static final int DOMAIN_EDITION = 11;
    public static final int COUNTRY_EDITION_V6 = 12;
    public static final int ASNUM_EDITION_V6 = 21;
    public static final int ISP_EDITION_V6 = 22;
    public static final int ORG_EDITION_V6 = 23;
    public static final int DOMAIN_EDITION_V6 = 24;
    public static final int CITY_EDITION_REV1_V6 = 30;
    public static final int CITY_EDITION_REV0_V6 = 31;
    public static final int NETSPEED_EDITION_REV1 = 32;
    public static final int NETSPEED_EDITION_REV1_V6 = 33;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    private final String info;

    /**
     * Creates a new DatabaseInfo object given the database info String.
     *
     * @param info
     */
    public DatabaseInfo(String info) {
        this.info = info;
    }

    public int getType() {
        if (info == null || info.length() == 0) {
            return COUNTRY_EDITION;
        } else {
            // Get the type code from the database info string and then
            // subtract 105 from the value to preserve compatability with
            // databases from April 2003 and earlier.
            return Integer.parseInt(info.substring(4, 7)) - 105;
        }
    }

    /**
     * Returns true if the database is the premium version.
     *
     * @return true if the premium version of the database.
     */
    public boolean isPremium() {
        return !info.contains("FREE");
    }

    /**
     * Returns the date of the database.
     *
     * @return the date of the database.
     */
    public Date getDate() {
        for (int i = 0; i < info.length() - 9; i++) {
            if (Character.isWhitespace(info.charAt(i))) {
                String dateString = info.substring(i + 1, i + 9);
                try {
                    synchronized (formatter) {
                        return formatter.parse(dateString);
                    }
                } catch (ParseException pe) {
                }
                break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return info;
    }
}
