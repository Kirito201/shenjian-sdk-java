package io.shenjian.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.ApiResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShenjianApi {
    private static final String BASE_URL = "http://api.shenjian.io/?appid=";
    private static final String CHARSET_NAME = "utf-8";
    private static final Logger LOG = LoggerFactory.getLogger(ShenjianApi.class);

    private String appId;

    public ShenjianApi(String appId) {
        this.appId = appId;
    }

    public ApiResponse callApi(Map<String, String> params) throws ShenjianException, IOException {
        String url = BASE_URL + appId;
        CloseableHttpResponse response = null;
        String result;
        ApiResponse responseMessage = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            //设置参数
            if (params != null) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> elem : params.entrySet()) {
                    list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHARSET_NAME);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new ShenjianException("Response code " + response.getStatusLine().getStatusCode() + "." + response.getStatusLine().getReasonPhrase(), response.getStatusLine().getStatusCode());
            }
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity, CHARSET_NAME);
            responseMessage = JSON.parseObject(result, ApiResponse.class);
            if (responseMessage == null) {
                throw new ShenjianException("接口返回异常");
            }
            if (responseMessage.getErrorCode() != 0) {
                //请求失败
                throw new ShenjianException("ErrorCode : " + responseMessage.getErrorCode().toString() + "." + responseMessage.getReason(), responseMessage.getErrorCode());
            }
        } catch (JSONException e) {
            throw new ShenjianException("接口返回异常", e);
        } catch (UnsupportedEncodingException e) {
            LOG.warn("error:", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.warn("response can't be closed.", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LOG.warn("http client can't be closed", e);
                }
            }
        }
        return responseMessage;
    }

}
