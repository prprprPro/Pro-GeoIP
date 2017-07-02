package cn.szzxol.pro.geoip.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import cn.szzxol.pro.geoip.IP;
import java.net.InetAddress;
import org.bukkit.ChatColor;

/**
 *
 * @author I_promise
 */
public class PlayerJoin implements Listener {

    @EventHandler
    public void Playerjoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        GeoTips(player);
    }

    public void GeoTips(Player player) {
        InetAddress address = player.getAddress().getAddress();
        IP ip = new IP(address);
        player.sendMessage((new StringBuilder()).append(ChatColor.WHITE).append(ChatColor.BOLD).append("来自 ").append(ChatColor.AQUA).append(ChatColor.BOLD).append(ip.getCity()).append(",").append(ip.getRegion()).append(",").append(ip.getCountry()).append(ChatColor.WHITE).append(ChatColor.BOLD).append("的朋友你好哇！").toString());
    }

}
