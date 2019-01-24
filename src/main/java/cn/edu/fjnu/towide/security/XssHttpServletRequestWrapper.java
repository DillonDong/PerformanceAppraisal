package cn.edu.fjnu.towide.security;

import cn.edu.fjnu.towide.util.JsoupUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by cao zheng xi Cotter on 2018/9/6.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    Logger logger= LoggerFactory.getLogger(getClass());

    HttpServletRequest orgRequest = null;
    private boolean isIncludeRichText = false;


    private  byte[] body;

    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) throws IOException {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;

        try {
            byte [] bytes =readBytes(request.getReader(), "utf-8");
            String requestStr=new String(bytes);
            if (StringUtils.isNotBlank(requestStr)) {
                requestStr = JsoupUtil.clean(requestStr);
            }
            body = requestStr.getBytes();
        }catch (Exception e){
            logger.debug("------XssHttpServletRequestWrapper--request is null--------"+e.getMessage());

        }


//        BufferedReader  streamReader=new BufferedReader(new InputStreamReader(super.getInputStream(),"UTF-8"));
//        StringBuilder stringBuilder=new StringBuilder();
//
//        String inputStr;
//        while ((inputStr=streamReader.readLine())!=null){
//            stringBuilder.append(inputStr);
//        }
//
//        String requestStr=stringBuilder.toString();
//        logger.debug("=========requestStr========="+requestStr);
//
    }


    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {

        if(("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText){
            return super.getParameter(name);
        }
        name = JsoupUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {

        String[] arr = super.getParameterValues(name);
        if(arr != null){
            for (int i=0;i<arr.length;i++) {
                arr[i] = JsoupUtil.clean(arr[i]);
            }
        }
        return arr;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream stream = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return stream.read();
            }
        };
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = JsoupUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    /**
     * 通过BufferedReader和字符编码集转换成byte数组
     * @param br
     * @param encoding
     * @return
     * @throws IOException
     */
    private byte[] readBytes(BufferedReader br,String encoding) throws IOException{
        String str = null,retStr="";
        while ((str = br.readLine()) != null) {
            retStr += str;
        }
        if (StringUtils.isNotBlank(retStr)) {
            return retStr.getBytes(Charset.forName(encoding));
        }
        return null;
    }
}
