//package com.oliver.service.impl;
//
//import com.oliver.service.WeChatPayService;
//import com.oliver.utils.WxUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transaction;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
///**
// * Created by suneo.
// * User: neo
// * Date: 10/02/2018
// * Time: 4:33 PM
// * Describe:
// * WeChatPayService 接口实现
// */
//@Service
//public class WeChatPayServiceImpl implements WeChatPayService {
//
//
//    @Value("${wxpay.appid}")
//    private String appid;
//
//    @Value("${wxpay.mchid}")
//    private String mch_id;
//
//    @Value("${wxpay.notify_url}")
//    private String notifyUrl;
//
//    @Value("${wxpay.key}")
//    private String key;
//
//    // 下单 API 地址
//    private String placeUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//
//    @Autowired
//    private WxUtils wxUtils;
//    private OrderService orderService;  // 订单处理类，负责处理一个订单成功／失败后的业务逻辑
//    @Autowired
//    private TradeService tradeService;  // 支付订单持久化类，负责订单信息的存取，判断该订单是否已经被关闭（#hasProcessed，被执行完毕）
//
//    @Override
//    public Map createOrder(String orderId, BigDecimal price, String body, String ipAddress) throws IOException {
//        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
//        parameters.put("appid", appid);
//        parameters.put("mch_id", mch_id);
//        parameters.put("device_info", "WEB"); // 默认 "WEB"
//        parameters.put("body", body);
//        parameters.put("nonce_str", wxUtils.gen32RandomString()); // 32 位随机字符串
//        parameters.put("notify_url", notifyUrl);
//        parameters.put("out_trade_no", orderId);
//        parameters.put("total_fee", price.multiply(BigDecimal.valueOf(100)).intValue());
////        parameters.put("total_fee", 1); // 测试时，将支付金额设置为 1 分钱
//        parameters.put("spbill_create_ip", ipAddress);
//        parameters.put("trade_type", "APP");
//        parameters.put("sign", wxUtils.createSign(parameters, key)); // sign 必须在最后
//        String result = wxUtils.executeHttpPost(placeUrl, parameters); // 执行 HTTP 请求，获取接收的字符串（一段 XML）
//        return wxUtils.createSign2(result, key);
//    }
//
//    @Transactional
//    @Override
//    public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 预先设定返回的 response 类型为 xml
//        response.setHeader("Content-type", "application/xml");
//        // 读取参数，解析Xml为map
//        Map<String, String> map = wxUtils.transferXmlToMap(wxUtils.readRequest(request));
//        // 转换为有序 map，判断签名是否正确
//        boolean isSignSuccess = wxUtils.checkSign(new TreeMap<String, Object>(map), key);
//        if (isSignSuccess) {
//            // 签名校验成功，说明是微信服务器发出的数据
//            String orderId = map.get("out_trade_no");
//            if (tradeService.hasProcessed(orderId)) // 判断该订单是否已经被接收处理过
//                return success();
//            // 可在此持久化微信传回的该 map 数据
//            //..
//            if (map.get("return_code").equals("SUCCESS")) {
//                if (map.get("result_code").equals("SUCCESS")) {
//                    orderService.finishOrder(orderId);  // 支付成功
//                } else {
//                    orderService.failOrder(orderId);    // 支付失败
//                }
//            }
//            return success();
//        } else {
//            // 签名校验失败（可能不是微信服务器发出的数据）
//            return fail();
//        }
//    }
//
//    String fail() {
//        return "<xml>\n" +
//                "  <return_code><![CDATA[FAIL]]></return_code>\n" +
//                "  <return_msg><![CDATA[]]></return_msg>\n" +
//                "</xml>";
//    }
//
//    String success() {
//        return "<xml>\n" +
//                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
//                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
//                "</xml>";
//    }
//}
