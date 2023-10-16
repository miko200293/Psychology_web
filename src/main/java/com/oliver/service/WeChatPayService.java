package com.oliver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by suneo.
 * User: neo
 * Date: 10/02/2018
 * Time: 4:34 PM
 * Describe:
 */

public interface WeChatPayService {

    /**
     * 创建支付订单，对应文章中第一步，由合理的业务 service 调用，
     * 比如：购买商品业务中：
     * 用户选择商品提交服务器 ->
     * 服务器计算价格，生成业务订单、生成支付订单 ->
     * 用户支付 ->
     * 服务器确认支付结果，更新支付订单状态、更新业务订单状态 ->
     * 绑定物流单 -> ...
     * 此处，应该是在第二步中生成该支付订单
     *
     * @param orderId   公司业务订单号
     * @param price     价格
     * @param body      主题信息
     * @param ipAddress 客户端APP IP 地址
     * @return 返回的信息直接发给客户端即可
     * @throws IOException
     */
    Map createOrder(String orderId, BigDecimal price, String body, String ipAddress) throws IOException;

    /**
     * 微信服务器调用该请求，进行数据异步传回作用
     *
     * @param request
     * @param response
     * @return 一个代表接受成功／失败的 XML 信息（失败原因应该是：签名失败，成功则表示确认收到数据，微信不需要再发数据到服务器）
     * @throws Exception
     */
    String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception;
}