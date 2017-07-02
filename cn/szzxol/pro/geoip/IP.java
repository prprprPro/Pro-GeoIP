package cn.szzxol.pro.geoip;

import static cn.szzxol.pro.geoip.GeoIP.folder;
import static cn.szzxol.pro.geoip.Download.Download;
import static cn.szzxol.pro.geoip.UnZip.unZip;
import cn.szzxol.pro.geoip.geoapi.Location;
import cn.szzxol.pro.geoip.geoapi.LookupService;
import cn.szzxol.pro.geoip.geoapi.regionName;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import static org.bukkit.Bukkit.getLogger;

/**
 *
 * @author I_promise
 */
public class IP {

    private final String Country;
    private final String Region;
    private final String City;

    public IP(InetAddress ip) {
        String country = "";
        String region = "";
        String city = "";
        File DAT = new File(folder + "/City.dat");
        if (!DAT.exists()) {
            this.Country = "未知国度";
            this.Region = "未知省份";
            this.City = "未知城市";
            return;
        }
        try {
            LookupService service = new LookupService(folder + "City.dat", LookupService.GEOIP_MEMORY_CACHE);
            Location location = service.getLocation(ip);
            if (location.countryName == null || "".equals(location.countryName) || regionName.regionNameByCode(location.countryCode, location.region) == null || "".equals(regionName.regionNameByCode(location.countryCode, location.region)) || location.city == null || "".equals(location.city)) {
                country = "未知国度";
                region = "未知省份";
                city = "未知城市";
            } else {
                country = location.countryName;
                region = regionName.regionNameByCode(location.countryCode, location.region);
                city = location.city;
                service.close();
            }
        } catch (IOException e) {
        }
        this.Country = country;
        this.Region = region;
        this.City = city;
    }

    public String getCountry() {
        return Country;
    }

    public String getRegion() {
        return Region;
    }

    public String getCity() {
        return City;
    }

    public static void DownloadDat() {
        getLogger().info("[pro-GeoIP] 正在下载IP数据库...");
        try {
            Download();
        } catch (IOException ex) {
        }
        getLogger().info("[pro-GeoIP] IP数据库下载完成");
        getLogger().info("[pro-GeoIP] 正在解压IP数据库...");
        unZip("City.dat.gz");
        getLogger().info("[pro-GeoIP] IP数据库解压完成");
    }

}
