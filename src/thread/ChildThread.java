package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.pan.framework.util.JsoupKit;

import config.NBAConfig;
import test.MainThread;

/**
 * @author Pan
 *
 */
public class ChildThread extends Thread {
    private static int num = NBAConfig.startNum;
    private final int LASTNUM = NBAConfig.endNum;
    private final String LOCK = "LOCK";
    private String[] elementIds = null;
    private Map<String, List<String>> resultMap = null;

    public ChildThread(Map<String, List<String>> resultMap, String[] elementIds) {
        this.resultMap = resultMap;
        this.elementIds = elementIds;
    }

    @Override
    public void run() {
        List<String> urlList = new ArrayList<>();
        while (true) {
            if (num > LASTNUM) {
                break;
            }
            synchronized (LOCK) {
                ++num;
            }
            try {
                Document doc = JsoupKit.getDocument(NBAConfig.url + num);
                List<String> playerList = JsoupKit.getPageValueMap(doc, true, elementIds,
                        new String[] { "位置", "身高", "体重", "评价", "所需精华", "暂不支持兑换" });
                if (!playerList.isEmpty()) {
                    String name = playerList.get(0);
                    System.out.println("ID: " + num + " 线程: " + Thread.currentThread().getName() + " 名称: " + name);
                    playerList.remove(name);
                    resultMap.put(name, playerList);
                }
            } catch (org.jsoup.HttpStatusException e) {
            } catch (java.net.ConnectException e) {
                urlList.add(NBAConfig.url + num);
            } catch (Exception e) {
                System.err.println("获取 " + NBAConfig.url + num + " 数据失败, 原因是: " + e.getMessage());
            }
        }
        System.out.println(urlList);
        MainThread.ConformityData(resultMap);
    }
}
