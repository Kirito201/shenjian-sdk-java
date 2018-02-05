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
import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.common.parser.ResponseParser;
import io.shenjian.sdk.model.*;
import org.apache.commons.codec.binary.Base64;

import java.util.*;

public class CrawlerOperation extends ShenjianOperation {

    public CrawlerOperation(Credentials credentials) {
        super(credentials);
    }

    public List<Crawler> listCrawler(int page, int pageSize) throws ShenjianException {
        String path = "crawler/list";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("page", String.valueOf(page));
        params.put("page_size", String.valueOf(pageSize));
        return doOperation(path, params, listCrawlerResponseParser);
    }

    private ResponseParser<List<Crawler>> listCrawlerResponseParser = new ResponseParser<List<Crawler>>() {
        @Override
        public List<Crawler> parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                ArrayList arrayList = (ArrayList) ((Map) data).get("list");
                List<Crawler> list = new ArrayList<Crawler>();
                for (Object anArrayList : arrayList) {
                    Crawler crawler = new Crawler();
                    if (anArrayList instanceof Map) {
                        Map map = (Map) anArrayList;
                        try {
                            crawler.setAppId((int) Math.ceil((Double)map.get("app_id")));
                            crawler.setName(String.valueOf(map.get("name")));
                            crawler.setInfo(String.valueOf(map.get("info")));
                            crawler.setType(String.valueOf(map.get("type")));
                            crawler.setStatus(AppStatus.fromValue(String.valueOf(map.get("status"))));
                            crawler.setCreateTime((int) Math.ceil((Double)map.get("time_create")));
                        } catch (IllegalArgumentException e) {
                            throw new ShenjianException("接口返回异常", e, responseMessage);
                        }
                        list.add(crawler);
                    }
                }
                return list;
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public Crawler createCrawler(String appName, String appInfo, String crawlerCode) throws ShenjianException {
        String path = "crawler/create";
        Map<String, String> params = new HashMap<String, String>();
        String code = new String(Base64.encodeBase64(crawlerCode.getBytes()));
        params.putAll(getPassportParams());
        params.put("app_name", appName);
        params.put("app_info", appInfo);
        params.put("code", code);
        return doOperation(path, params, createCrawlerResponseParser);
    }

    private ResponseParser<Crawler> createCrawlerResponseParser = new ResponseParser<Crawler>() {
        @Override
        public Crawler parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Crawler crawler = new Crawler();
                Map map = (Map) data;
                try {
                    crawler.setAppId(Integer.valueOf(String.valueOf(map.get("app_id"))));
                    crawler.setName(String.valueOf(map.get("name")));
                    crawler.setType("crawler");
                    crawler.setStatus(AppStatus.fromValue(String.valueOf(map.get("status"))));
                    crawler.setCreateTime((int) Math.ceil((Double)map.get("time_create")));
                    return crawler;
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public void deleteCrawler(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/delete";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }

    public void editCrawler(int appId, String appName, String appInfo) throws ShenjianException {
        String path = "crawler/" + appId + "/edit";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("app_name", appName);
        params.put("app_info", appInfo);
        doOperation(path, params, emptyResponseParser);
    }

    public void configCrawlerCustom(int appId, Map<String, Object> configMap) throws ShenjianException {
        String path = "crawler/" + appId + "/config/custom";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        Gson gson = new Gson();
        Iterator<Map.Entry<String, Object>> iterator = configMap.entrySet().iterator();
        Map.Entry<String, Object> configList;
        while (iterator.hasNext()) {
            configList = iterator.next();
            Object value = configList.getValue();
            String key = configList.getKey();
            if (String.valueOf(value).equals("true")) {
                params.put(key, "true");
            } else if (String.valueOf(value).equals("true")) {
                params.put(key, "false");
            } else if (value.getClass().isArray()) {
                params.put(key, gson.toJson(value));
            } else {
                params.put(key, String.valueOf(value));
            }
        }
        doOperation(path, params, emptyResponseParser);
    }

    public void startCrawler(int appId, int node, CrawlerTimer crawlerTimer) throws ShenjianException {
        String path = "crawler/" + appId + "/start";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.putAll(crawlerTimer.build());
        params.put("node", String.valueOf(node));
        doOperation(path, params, emptyResponseParser);
    }

    public void stopCrawler(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/stop";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }

    public void pauseCrawler(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/pause";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }

    public void resumeCrawler(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/resume";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }

    public AppStatus getCrawlerStatus(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/status";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, crawlerStatusResponseParser);
    }

    private ResponseParser<AppStatus> crawlerStatusResponseParser = new ResponseParser<AppStatus>() {
        @Override
        public AppStatus parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                try {
                    return AppStatus.fromValue(String.valueOf(((Map) data).get("status")));
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public float getCrawlerSpeed(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/speed";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, crawlerSpeedResponseParser);
    }

    private ResponseParser<Float> crawlerSpeedResponseParser = new ResponseParser<Float>() {
        @Override
        public Float parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Object money = ((Map) data).get("speed");
                if (money != null && !(money instanceof String)) {
                    try {
                        return Float.valueOf(money.toString());
                    } catch (NumberFormatException e) {
                        throw new ShenjianException("接口返回异常", e, responseMessage);
                    }
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public CrawlerNodeInfo changeCrawlerNode(int appId, int nodeDelta) throws ShenjianException {
        String path = "crawler/" + appId + "/node";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("node_delta", String.valueOf(nodeDelta));
        return doOperation(path, params, crawlerNodeResponseParser);
    }

    private ResponseParser<CrawlerNodeInfo> crawlerNodeResponseParser = new ResponseParser<CrawlerNodeInfo>() {
        @Override
        public CrawlerNodeInfo parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Map map = (Map) data;
                CrawlerNodeInfo crawlerNodeInfo = new CrawlerNodeInfo();
                try {
                    crawlerNodeInfo.setLeftNodes(Integer.getInteger(String.valueOf(map.get("node_left"))));
                    crawlerNodeInfo.setRunningNodes(Integer.getInteger(String.valueOf(map.get("node_running"))));
                    return crawlerNodeInfo;
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public CrawlerSource getCrawlerSource(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/source";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, crawlerSourceResponseParser);
    }

    private ResponseParser<CrawlerSource> crawlerSourceResponseParser = new ResponseParser<CrawlerSource>() {
        @Override
        public CrawlerSource parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Map map = (Map) data;
                CrawlerSource crawlerSource = new CrawlerSource();
                try {
                    crawlerSource.setAppId(Integer.valueOf(String.valueOf(map.get("app_id"))));
                    crawlerSource.setType(String.valueOf(map.get("type")));
                    crawlerSource.setCount((int) Math.ceil((Double)map.get("count")));
                    return crawlerSource;
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public void configCrawlerProxy(int appId, ProxyType proxyType) throws ShenjianException {
        String path = "crawler/" + appId + "/config/proxy";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("proxy_type", String.valueOf(proxyType.ordinal()));
        doOperation(path, params, emptyResponseParser);
    }

    public void configCrawlerHost(int appId, HostType hostType, int fileTypeFlag) throws ShenjianException {
        String path = "crawler/" + appId + "/config/host";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("host_type", String.valueOf(hostType.ordinal()));
        if ((FileType.IMAGE & fileTypeFlag) == FileType.IMAGE) {
            params.put("image", "true");
        }
        if ((FileType.TEXT & fileTypeFlag) == FileType.TEXT) {
            params.put("text", "true");
        }
        if ((FileType.AUDIO & fileTypeFlag) == FileType.AUDIO) {
            params.put("audio", "true");
        }
        if ((FileType.VIDEO & fileTypeFlag) == FileType.VIDEO) {
            params.put("video", "true");
        }
        if ((FileType.APPLICATION & fileTypeFlag) == FileType.APPLICATION) {
            params.put("application", "true");
        }
        doOperation(path, params, emptyResponseParser);
    }

    public Webhook getWebhookInfo(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/webhook/get";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, webhookInfoResponseParser);
    }

    private ResponseParser<Webhook> webhookInfoResponseParser = new ResponseParser<Webhook>() {
        @Override
        public Webhook parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Map map = (Map) data;
                Webhook webhook = new Webhook();
                try {
                    webhook.setUrl(String.valueOf(map.get("url")));
                    webhook.setEvents(new Gson().fromJson(String.valueOf(map.get("events")), String[].class));
                    return webhook;
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public void deleteCrawlerWebhook(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/webhook/delete";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }

    public void configWebhookInfo(int appId, String url, int eventFlag) throws ShenjianException {
        String path = "crawler/" + appId + "/webhook/set";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("url", url);
        if ((WebhookEventType.DATA_NEW & eventFlag) == WebhookEventType.DATA_NEW) {
            params.put("data_new", "true");
        }
        if ((WebhookEventType.DATA_UPDATED & eventFlag) == WebhookEventType.DATA_UPDATED) {
            params.put("data_updated", "true");
        }
        if ((WebhookEventType.MSG_CUSTOM & eventFlag) == WebhookEventType.MSG_CUSTOM) {
            params.put("msg_custom", "true");
        }
        doOperation(path, params, emptyResponseParser);
    }

    public AutoPublishStatus getAutoPublishStatus(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/autopublish/status";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, autoPublishStatusResponseParser);
    }

    private ResponseParser<AutoPublishStatus> autoPublishStatusResponseParser = new ResponseParser<AutoPublishStatus>() {
        @Override
        public AutoPublishStatus parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Map map = (Map) data;
                AutoPublishStatus autoPublishStatus = new AutoPublishStatus();
                try {
                    autoPublishStatus.setStatus(String.valueOf(map.get("status")));
                    autoPublishStatus.setMessage(String.valueOf(map.get("message")));
                    autoPublishStatus.setStopTime((int) Math.ceil((Double)map.get("time_stop")));
                    return autoPublishStatus;
                } catch (IllegalArgumentException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public void startAutoPublish(int appId, int[] publishId) throws ShenjianException {
        String path = "crawler/" + appId + "/autopublish/start";
        Map<String, String> params = new HashMap<String, String>();
        Gson gson = new Gson();
        params.putAll(getPassportParams());
        String[] str = new String[publishId.length];
        for (int i = 0; i < publishId.length; i++) {
            str[i] = String.valueOf(publishId[i]);
        }
        params.put("publish_id", gson.toJson(str));
        doOperation(path, params, emptyResponseParser);
    }

    public void stopAutoPublish(int appId) throws ShenjianException {
        String path = "crawler/" + appId + "/autopublish/stop";
        Map<String, String> params = getPassportParams();
        doOperation(path, params, emptyResponseParser);
    }
}
