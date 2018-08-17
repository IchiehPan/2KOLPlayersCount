package config;

import com.pan.framework.util.NumberKit;
import com.pan.framework.util.XMLKit;

/**
 * @author Pan
 */
public class NBAConfig {
    public static int startNum = 0;
    public static int endNum = 0;
    public static String url = XMLKit.getText("url");
    public static String elementCommonId = XMLKit.getText("elementCommonId");

    public static int cpuCore = 0;

    public static String filePath = XMLKit.getText("filePath");
    public static String fileName = XMLKit.getText("fileName");
    public static String fileExt = XMLKit.getText("fileExt");

    static {
        String firstNum = XMLKit.getText("startNum");
        String lastNum = XMLKit.getText("endNum");
        if (NumberKit.isPositiveInteger(firstNum)) {
            startNum = Integer.parseInt(firstNum);
        }
        if (NumberKit.isPositiveInteger(lastNum)) {
            endNum = Integer.parseInt(lastNum);
        }
        if (NumberKit.isPositiveInteger(XMLKit.getText("cpuCore"))) {
            cpuCore = Integer.parseInt(XMLKit.getText("cpuCore"));
        }
    }

}
