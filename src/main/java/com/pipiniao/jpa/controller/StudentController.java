package com.pipiniao.jpa.controller;

import com.pipiniao.jpa.bean.Student;
import com.pipiniao.jpa.dto.AuthUtil;
import com.pipiniao.jpa.dto.CheckoutUtil;
import com.pipiniao.jpa.service.impl.IStudentService;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Arrays;

@RestController
public class StudentController {
    public static final String TOKEN = "token";
    @Autowired
    IStudentService iStudentService;

    @ApiOperation(value = "根据name寻找人")
    @RequestMapping("/findN/{name}")
    public Student findByName(@PathVariable String name) {
        return iStudentService.findByName (name);
    }

    @RequestMapping("/token")
    public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signature = request.getParameter ("signature");
        String timestamp = request.getParameter ("timestamp");
        String nonce = request.getParameter ("nonce");
        String echostr = request.getParameter ("echostr");
            if (CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            } else {
                throw new Exception ("验证失败");
            }
    }

    @GetMapping("/sms")
    public void setToken()throws Exception{
        String phone="18000252855";
        Socket socket=new Socket ();
        socket.setSoTimeout (5000);
        socket.getOutputStream ().write (
                new String("GET http://10.10.25.65/openApi/user/verifyCode.action?phone=18000252855&verifyCodeType=1").getBytes ());
        InputStream is=socket.getInputStream ();
        StringBuilder str = new StringBuilder("");
        InputStreamReader isr = new InputStreamReader (is,"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            str.append(line + "\n");
            //这里必须自行判断跳出，因为socket是不会断开访问的
            if(line.contains("</html>")){
                break;
            }
        }
        br.close();
        isr.close();
        is.close();
    }
}