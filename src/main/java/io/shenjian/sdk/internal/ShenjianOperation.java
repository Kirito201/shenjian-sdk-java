/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.shenjian.sdk.internal;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.common.parser.ResponseParser;
import io.shenjian.sdk.model.Credentials;
import io.shenjian.sdk.model.Passport;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.text.DecimalFormat;
import java.util.*;

public abstract class ShenjianOperation {
    private static final String BASE_URL = "http://www.shenjian.io/rest/v3/";
    private static final String CHARSET_NAME = "utf-8";
    private static final Logger LOG = LoggerFactory.getLogger(ShenjianOperation.class);
    protected Credentials credentials;

    protected ShenjianOperation(Credentials credentials) {
        this.credentials = credentials;
    }

    protected ResponseParser<ResponseMessage> emptyResponseParser = new ResponseParser<ResponseMessage>() {
        @Override
        public ResponseMessage parse(ResponseMessage responseMessage) throws ShenjianException {
            return responseMessage;
        }
    };

    protected Map<String, String> getPassportParams() throws ShenjianException {
        Map<String, String> params = new HashMap<String, String>();
        Passport passport = credentials.generatePassport();
        params.put("user_key", passport.getUserKey());
        params.put("timestamp", String.valueOf(passport.getTimestamp()));
        params.put("sign", passport.getSign());
        return params;
    }

    protected static <T> T doOperation(String path, Map<String, String> params, ResponseParser<T> parser) throws ShenjianException {
        String url = BASE_URL + path;
        CloseableHttpResponse response = null;
        String result;
        ResponseMessage responseMessage = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> elem : params.entrySet()) {
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHARSET_NAME);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity, CHARSET_NAME);
            responseMessage = new Gson().fromJson(result, ResponseMessage.class);
            if (responseMessage.getCode() != 0) {
                //请求失败
                DecimalFormat format = new DecimalFormat("0.#");
                ShenjianException e = new ShenjianException("ErrorCode :" + format.format(responseMessage.getCode()) + " ; " + responseMessage.getReason(), responseMessage);
                logShenjianException(e);
                throw e;
            }
        } catch (NullPointerException e) {
            throw new ShenjianException("ErrorCode : 100001 ; 应用爬虫不存在", e, responseMessage);
        } catch (JsonSyntaxException e) {
            throw new ShenjianException("ErrorCode : 100001 ; 应用不存在", e, responseMessage);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return parser.parse(responseMessage);
        } catch (ShenjianException e) {
            logShenjianException(e);
            throw e;
        }
    }

    private static void logShenjianException(ShenjianException e) {
        LOG.warn(e.getMessage(), e);
    }
}
