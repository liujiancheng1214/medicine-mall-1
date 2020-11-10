package cn.jdcloud.medicine.mall.api.biz.pay.utils;

import cn.jdcloud.framework.utils.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

/**
 * Created by yanghuoyun on 2017/6/8.
 */
public class HttpUtil {

    public static final String UTF8 = "UTF-8";

    public static String getResponseBodyAsString(HttpResponse response) throws Exception{
        byte[] bytes = new byte[0];
        InputStream in = response.getEntity().getContent();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            int rc = in.read(buf);
            if (rc <= 0) {
                break;
            } else {
                bout.write(buf, 0, rc);
            }
        }
        bytes = bout.toByteArray();
        in.close();
        bout.close();
        // only fetch Content-Length and Last-Modified header
        String encoding = null;
        Header contentType = response.getFirstHeader("Content-Type");
        if (contentType==null) {
            encoding = UTF8;
        }else {
            encoding = getEncodingFromContentType(contentType.getValue());
        }
        return new String(bytes,encoding);
    }

    public static String getEncodingFromContentType(String contentType)
    {
        String encoding = null;
        if (StringUtils.isEmpty(contentType))
        {
            return UTF8;
        }
        StringTokenizer tok = new StringTokenizer(contentType, ";");
        if (tok.hasMoreTokens())
        {
            tok.nextToken();
            while (tok.hasMoreTokens())
            {
                String assignment = tok.nextToken().trim();
                int eqIdx = assignment.indexOf('=');
                if (eqIdx != -1)
                {
                    String varName = assignment.substring(0, eqIdx).trim();
                    if ("charset".equalsIgnoreCase(varName))
                    {
                        String varValue = assignment.substring(eqIdx + 1).trim();
                        if (varValue.startsWith("\"") && varValue.endsWith("\""))
                        {
                            // substring works on indices
                            varValue = varValue.substring(1, varValue.length() - 1);
                        }
                        if (Charset.isSupported(varValue))
                        {
                            encoding = varValue;
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(encoding))
        {
            return UTF8;
        }
        return encoding;
    }
}
