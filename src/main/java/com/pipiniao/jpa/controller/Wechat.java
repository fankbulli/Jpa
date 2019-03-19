package com.pipiniao.jpa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pipiniao.jpa.dto.AuthUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Wechat {
    @GetMapping("/oauth")
    @ResponseBody
    public String loginInit() throws Exception {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl="http://qjsbq7.natappfree.cc/getCode";
        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AuthUtil.APPID+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        System.out.println (url);
        return url;
    }
    @GetMapping("/getCode")
    @ResponseBody
    public Map<String,String> getAccessToken(HttpServletRequest request, HttpServletResponse response){
        String code=request.getParameter ("code");
        System.out.println (code);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID +
                "&secret="+AuthUtil.SECERT+"&code="+code+"&grant_type=authorization_code";
        System.out.println (url);
        OkHttpClient mOkHttpClient = new OkHttpClient ();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        FormBody formBody = formBodyBuilder.build();
        Request request1 = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        JSONObject access=null;
        JSONObject user=null;
        Map<String,String> map=new HashMap<> ();
        map.put ("msg","ok");
        try (Response response1 = mOkHttpClient.newCall(request1).execute()) {
            String result=response1.body().string ();
            access=JSONObject.parseObject (result);
            String access_token=access.getString ("access_token");
            String openid=access.getString ("openid");
            map.put ("openid",openid);
            String new_url="https://api.weixin.qq.com/sns/userinfo?access_token="+ access_token+"&openid="+openid+"&lang=zh_CN";
            FormBody.Builder formBodyBuilder1 = new FormBody.Builder();
            FormBody formBody1= formBodyBuilder1.build();
            Request request2 = new Request
                    .Builder()
                    .post(formBody1)
                    .url(new_url)
                    .build();
            try (Response response2 = mOkHttpClient.newCall(request2).execute()) {
                String result1=response2.body().string();
                System.out.println (result1);
                user=JSON.parseObject (result1);
                String nickname =user.getString("nickname");
                String headImgUrl = user.getString("headimgurl");
                String city=user.getString ("city");
                String provice=user.getString ("province");
                String country=user.getString ("country");
                String sex=Integer.parseInt (user.getString ("sex"))==1?"男":"女";
                map.put ("nickname",nickname);
                map.put ("url",headImgUrl);
                map.put ("country",country);
                map.put ("province",provice);
                map.put ("city",city);
                map.put ("sex",sex);
            }catch (Exception e) {
                e.printStackTrace ();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
