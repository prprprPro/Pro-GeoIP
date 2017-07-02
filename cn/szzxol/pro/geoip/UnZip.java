package cn.szzxol.pro.geoip;

import static cn.szzxol.pro.geoip.GeoIP.folder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author I_promise
 */
public class UnZip {

    public static void unZip(String FileName) {
        try {
            String sourcedir = folder + "/" + FileName;
            //建立gzip压缩文件输入流 
            FileInputStream fin = new FileInputStream(sourcedir);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流  
            String ouputfile = sourcedir.substring(0, sourcedir.lastIndexOf('.'));
            FileOutputStream fout = new FileOutputStream(ouputfile);

            int num;
            byte[] buf = new byte[1024];
            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }

            gzin.close();
            fout.close();
            fin.close();

        } catch (Exception ex) {
        }
        File file = new File(folder + "/" + FileName);
        file.delete();
    }
}
