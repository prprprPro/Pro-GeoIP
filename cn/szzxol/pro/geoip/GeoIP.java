package cn.szzxol.pro.geoip;

import static cn.szzxol.pro.geoip.IP.DownloadDat;
import cn.szzxol.pro.geoip.listener.PlayerJoin;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GeoIP extends JavaPlugin {

    public static FileConfiguration DefaultConfig;
    public static String version;
    public static File folder;

    @Override
    public void onEnable() {
        getLogger().info("插件正在加载...");
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        folder = this.getDataFolder();
        this.saveDefaultConfig();
        DefaultConfig = this.getConfig();
        version = DefaultConfig.getString("Version");
        File DAT = new File(folder + "/City.dat");
        if (!DAT.exists()) {
            DownloadDat();
        }
        getLogger().info("插件加载完成...");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件卸载完成...");
    }

    public static GeoIP instance;

    public GeoIP() {
        instance = this;
    }
}
