package com.kuaidaili;

import com.pan.framework.util.JsoupKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.logging.Logger;

public class ProxyThread {
    public static Logger logger = Logger.getLogger(ProxyThread.class.getSimpleName());

    public static void main(String[] args) {
        logger.info("准备获取数据......");

        Document doc = null;
        try {
            doc = JsoupKit.getDocument("https://www.kuaidaili.com/free/inha/1/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            logger.warning("爬虫失败......");
            return;
        }

        File file = new File("inha1.html");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("文件路径在: " + file.getAbsolutePath());

        OutputStream outputStream = null;
        OutputStreamWriter writer = null;
        try {
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.append(doc.outerHtml());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        logger.config("config......");
//        logger.severe("severe......");
//        logger.fine("fine......");
//        logger.finer("finer......");
//        logger.finest("finest......");

        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ipElements = doc.getElementsByAttributeValue("data-title", "IP");
        ipElements.forEach(item -> logger.info(item.text()));

        Elements portElements = doc.getElementsByAttributeValue("data-title", "PORT");
        portElements.forEach(item -> logger.info(item.text()));

        Elements anonymousElements = doc.getElementsByAttributeValue("data-title", "匿名度");
        anonymousElements.forEach(item -> logger.info(item.text()));

        Elements typeElements = doc.getElementsByAttributeValue("data-title", "类型");
        typeElements.forEach(item -> logger.info(item.text()));

        Elements locateElements = doc.getElementsByAttributeValue("data-title", "位置");
        locateElements.forEach(item -> logger.info(item.text()));

        Elements speedElements = doc.getElementsByAttributeValue("data-title", "响应速度");
        speedElements.forEach(item -> logger.info(item.text()));

        Elements timeElements = doc.getElementsByAttributeValue("data-title", "最后验证时间");
        timeElements.forEach(item -> logger.info(item.text()));


    }
}
