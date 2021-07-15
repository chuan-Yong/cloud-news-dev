package com.cloud.utils;

import java.net.URLEncoder;

import com.cloud.utils.extend.BinstdResource;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InformationUtils {

    @Autowired
    private BinstdResource binstdResource;

    public static final String content = "您的手机验证码为@，1分钟内有效。请不要把此验证码泄露给任何人。【云媒体】";

    public  void sendMessage(String mobile,String code) throws Exception {
        String result = null;
        String url = binstdResource.getUrl() + "?mobile=" + mobile + "&content="
                + URLEncoder.encode("您的手机验证码为"+code+"，1分钟内有效。请不要把此验证码泄露给任何人。【云媒体】", "utf-8") + "&appkey="
                + binstdResource.getAppKey();
        try {
            result = HttpUtil.sendGet(url, "utf-8");
            JSONObject json = JSONObject.fromObject(result);
            if (json.getInt("status") != 0) {
                System.out.println(json.getString("msg"));
            } else {
                JSONObject resultarr = json.optJSONObject("result");
                String count = resultarr.getString("count");
                String accountid = resultarr.getString("accountid");
                System.out.println(count + " " + accountid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

