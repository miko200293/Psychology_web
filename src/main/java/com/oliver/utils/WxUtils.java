package com.oliver.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by suneo.
 * User: neo
 * Date: 29/01/2018
 * Time: 5:21 PM
 * Describe:
 * <编码格式统一为 UTF-8>
 * <p>
 * 便于使用，将所有的工具方法都集中在此，包含：
 * 1. 执行 HTTP POST 请求，返回执行结果的 String
 * 2. 创建签名（为下单数据创建）
 * 3. 创建签名（为 APP 创建）
 * 4. 检验签名
 * 5. 读取 HTTP Request 内容
 * 6. 读取 HTTP Response 内容
 * 7. 将 Map 转化为 Xml
 * 8. 将 Xml 转化为 Map
 * 9. 生成 32 位随机字符串
 * 10. MD5 签名
 */
@Service
public class WxUtils {

    /**
     * 执行 POST 方法的 HTTP 请求
     *
     * @param url
     * @param parameters
     * @return
     * @throws IOException
     */
    public String executeHttpPost(String url, SortedMap<String, Object> parameters) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-type", "application/xml");
        request.setHeader("Accept", "application/xml");
        request.setEntity(new StringEntity(transferMapToXml(parameters), "UTF-8"));
        HttpResponse response = client.execute(request);
        return readResponse(response);
    }


    /**
     * 第一次签名
     *
     * @param parameters 数据为服务器生成，下单时必须的字段排序签名
     * @param key
     * @return
     */
    public String createSign(SortedMap<String, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        return encodeMD5(sb.toString());
    }

    /**
     * 第二次签名
     *
     * @param result 数据为微信返回给服务器的数据（XML 的 String），再次签名后传回给客户端（APP）使用
     * @param key    密钥
     * @return
     * @throws IOException
     */
    public Map createSign2(String result, String key) throws IOException {
        SortedMap<String, Object> map = new TreeMap<>(transferXmlToMap(result));
        Map app = new HashMap();
        app.put("appid", map.get("appid"));
        app.put("partnerid", map.get("mch_id"));
        app.put("prepayid", map.get("prepay_id"));
        app.put("package", "Sign=WXPay");                   // 固定字段，保留，不可修改
        app.put("noncestr", map.get("nonce_str"));
        app.put("timestamp", new Date().getTime() / 1000);  // 时间为秒，JDK 生成的是毫秒，故除以 1000
        app.put("sign", createSign(new TreeMap<>(app), key));
        return app;
    }

    /**
     * 验证签名是否正确
     *
     * @return boolean
     * @throws Exception
     */
    public boolean checkSign(SortedMap<String, Object> parameters, String key) throws Exception {
        String signWx = parameters.get("sign").toString();
        if (signWx == null) return false;
        parameters.remove("sign"); // 需要去掉原 map 中包含的 sign 字段再进行签名
        String signMe = createSign(parameters, key);
        return signWx.equals(signMe);
    }

    /**
     * 读取 request body 内容作为字符串
     *
     * @param request
     * @return
     * @throws IOException
     */
    public String readRequest(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String str;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    /**
     * 读取 response body 内容为字符串
     */
    public String readResponse(HttpResponse response) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String result = new String();
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

    /**
     * 将 Map 转化为 XML
     *
     * @param map
     * @return
     */
    public String transferMapToXml(SortedMap<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            sb.append("<").append(key).append(">")
                    .append(map.get(key))
                    .append("</").append(key).append(">");
        }
        return sb.append("</xml>").toString();
    }

    /**
     * 将 XML 转化为 map
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public Map transferXmlToMap(String strxml) throws IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(in);
        } catch (JDOMException e) {
            throw new IOException(e.getMessage()); // 统一转化为 IO 异常输出
        }
        // 解析 DOM
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        //关闭流
        in.close();
        return m;
    }

    // 辅助 transferXmlToMap 方法递归提取子节点数据
    private String getChildrenText(List<Element> children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator<Element> it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }


    /**
     * 生成 32 位随机字符串，包含：数字、字母大小写
     *
     * @return
     */
    public String gen32RandomString() {
        char[] dict = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(String.valueOf(dict[(int) (Math.random() * 36)]));
        }
        return sb.toString();
    }

    /**
     * MD5 签名
     *
     * @param str
     * @return 签名后的字符串信息
     */
    public String encodeMD5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = (str).getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // 辅助 encodeMD5 方法实现
    private String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}