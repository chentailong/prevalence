package com.lon.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.lon.common.BaseContext;
import com.lon.config.PayConfig;
import com.lon.config.WebSocket;
import com.lon.entity.AliReturnPay;
import com.lon.entity.Pay;
import com.lon.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ctl
 */

@Controller
@Slf4j
@CrossOrigin
@RequestMapping("/alipay")
public class AliPayController {

    @Resource
    private AlipayClient alipayClient;
    @Resource
    private WebSocket webSocket;

    @Autowired
    private OrdersService ordersService;

    private Pay pays;

    private JSONArray parseMessage = null;

    private final String alipay = "http://zwkp6b.natappfree.cc/alipay/call";

    @ResponseBody
    @PostMapping("/createQR")
    public String send(@RequestBody Pay pay) throws AlipayApiException {
        String message = pay.getMessage();
        parseMessage = (JSONArray) JSONArray.toJSON(JSON.parse(message));
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipay);
        request.setReturnUrl(alipay);
        pays = pay;
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + System.currentTimeMillis() / 1000 + Math.round((Math.random() + 1) * 1000) + "\","
                + "\"total_amount\":\"" + pay.getMoney() + "\","
                + "\"subject\":\"" + pay.getTitle() + "\"}");

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("支付API调用成功");
            return response.getQrCode();
        } else {
            log.info("支付API调用失败");
        }
        return "";
    }

    /**
     * 支付宝回调函数
     *
     * @param request
     * @param response
     * @param returnPay
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/call")
    public void call(HttpServletRequest request, HttpServletResponse response, AliReturnPay returnPay) throws Exception {
        response.setContentType("type=text/html;charset=UTF-8");
        log.info("支付宝的的回调函数被调用");
        if (!PayConfig.checkSign(request)) {
            log.info("验签失败");
            response.getWriter().write("failture");
            return;
        }
        if (returnPay == null) {
            log.info("支付宝的returnPay返回为空");
            response.getWriter().write("success");
            return;
        }
        if ("TRADE_SUCCESS".equals(returnPay.getTrade_status())) {
            log.info("支付宝的支付状态为TRADE_SUCCESS");
            ordersService.submit(pays, returnPay.getOut_trade_no(),parseMessage);
            webSocket.sendMessage("true");
        }
        response.getWriter().write("success");
    }

}
