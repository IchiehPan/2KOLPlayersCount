package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pan.framework.util.ListKit;
import com.pan.framework.util.TextFileKit;

import config.NBAConfig;
import thread.ChildThread;

/**
 * @author Pan
 *
 */
public class MainThread {
    private final static String elementCommonId = NBAConfig.elementCommonId;
    private static Map<String, List<String>> resultMap = new HashMap<>();
    private final static String FILENAME = "球员属性.csv";

    public static void main(String[] args) {
        System.out.println("准备获取数据......");
        String[] elementIds = getElementIds();
        for (int i = 0; i < NBAConfig.cpuCore; i++) {
            ChildThread childThread = new ChildThread(resultMap, elementIds);
            childThread.start();
        }
    }

    public synchronized static void ConformityData(Map<String, List<String>> map) {
        String filePath = MainThread.class.getClass().getResource(File.separator).getPath();
        Path pathName = Paths.get(filePath + FILENAME);
        try {
            TextFileKit excelUtils = new TextFileKit(pathName);
            System.out.println("一共有 " + map.size() + " 位球员数据.");
            excelUtils.write("姓名,位置,身高,体重,评价,精华,速度,扣篮,力量,传球,靠打,控球,近投,防守,中投,防板,远投,盖帽\r\n");
            for (Entry<String, List<String>> entry : map.entrySet()) {
                excelUtils.write(entry.getKey() + "," + ListKit.list2String(entry.getValue()) + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("-------结束生成文件-------");
        System.exit(0);
    }

    /**
     * @return 要遍历的元素Id[]
     */
    private static String[] getElementIds() {
        String[] elementIds = new String[12];
        for (int i = 0; i < 12; i++) {
            elementIds[i] = elementCommonId + i;
        }
        return elementIds;
    }
}
