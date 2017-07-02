package cn.szzxol.pro.geoip;

import static cn.szzxol.pro.geoip.GeoIP.DefaultConfig;
import static cn.szzxol.pro.geoip.GeoIP.folder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.bukkit.Bukkit.getLogger;

/**
 *
 * @author I_promise
 */
public class Download {

    private static int length = 0;
    private static int downloadlength = 0;
    private static int per = 0;
    private static int report = 0;

    public static void Download() throws IOException {
        String urlStr = DefaultConfig.getString("Database.DownloadURL");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒  
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        length = conn.getContentLength();
        //得到输入流  
        InputStream inputStream = conn.getInputStream();
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);

        //文件保存位置  
        File saveDir = folder;
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + "City.dat.gz");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            downloadlength += len;
            per = 100 * downloadlength / length;
            if (per >= report + 5) {
                getLogger().info("[pro-GeoIP] 下载进度：" + per + "%");
                report += 5;
            }
        }
        bos.close();
        return bos.toByteArray();
    }
}
