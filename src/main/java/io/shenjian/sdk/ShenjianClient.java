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

package io.shenjian.sdk;

import io.shenjian.sdk.internal.*;
import io.shenjian.sdk.model.Crawler;
import io.shenjian.sdk.model.*;

import java.util.List;
import java.util.Map;

/**
 * sdk调用入口
 */
public class ShenjianClient implements Shenjian {

    private Credentials credentials;

    /* The miscellaneous Shenjian operations */
    private UserOperation userOperation;
    private AppOperation appOperation;
    private CrawlerOperation crawlerOperation;


    public ShenjianClient(String key, String secret) {
        this.credentials = new Credentials(key, secret);
        initOperations();
    }

    private void initOperations() {
        this.userOperation = new UserOperation(this.credentials);
        this.appOperation = new AppOperation(this.credentials);
        this.crawlerOperation = new CrawlerOperation(this.credentials);
    }

    @Override
    public float getBalance() throws ShenjianException {
        return this.userOperation.getBalance();
    }

    @Override
    public NodeInfo getNodeInfo() throws ShenjianException {
        return this.userOperation.getNodeInfo();
    }

    @Override
    public List<App> listApp(int page, int pageSize) throws ShenjianException {
        return this.appOperation.listApp(page, pageSize);
    }

    @Override
    public List<Crawler> listCrawler(int page, int pageSize) throws ShenjianException {
        return this.crawlerOperation.listCrawler(page, pageSize);
    }

    @Override
    public Crawler createCrawler(String appName, String appInfo, String crawlerCode) {
        return this.crawlerOperation.createCrawler(appName, appInfo, crawlerCode);
    }

    @Override
    public void deleteCrawler(int appId) throws ShenjianException {

        this.crawlerOperation.deleteCrawler(appId);
    }

    @Override
    public void editCrawler(int appId, String appName, String appInfo) throws ShenjianException {
        this.crawlerOperation.editCrawler(appId, appName, appInfo);
    }

    @Override
    public void configCrawlerCustom(int appId, Map<String, Object> configMap) throws ShenjianException {
        this.crawlerOperation.configCrawlerCustom(appId, configMap);
    }

    @Override
    public void startCrawler(int appId, int node, CrawlerTimer crawlerTimer) throws ShenjianException {
        this.crawlerOperation.startCrawler(appId, node, crawlerTimer);
    }

    @Override
    public void startCrawler(int appId, CrawlerTimer crawlerTimer) throws ShenjianException {
        this.startCrawler(appId, 1, crawlerTimer);
    }

    @Override
    public void startCrawler(int appId, int node) throws ShenjianException {
        this.startCrawler(appId, node, null);
    }

    @Override
    public void startCrawler(int appId) throws ShenjianException {
        this.startCrawler(appId, 1, null);
    }

    @Override
    public void stopCrawler(int appId) throws ShenjianException {
        this.crawlerOperation.stopCrawler(appId);

    }

    @Override
    public void pauseCrawler(int appId) throws ShenjianException {
        this.crawlerOperation.pauseCrawler(appId);
    }

    @Override
    public void resumeCrawler(int appId) throws ShenjianException {
        this.crawlerOperation.resumeCrawler(appId);
    }

    @Override
    public AppStatus getCrawlerStatus(int appId) throws ShenjianException {
        return this.crawlerOperation.getCrawlerStatus(appId);
    }

    @Override
    public float getCrawlerSpeed(int appId) throws ShenjianException {
        return this.crawlerOperation.getCrawlerSpeed(appId);
    }

    @Override
    public CrawlerNodeInfo changeCrawlerNode(int appId, int nodeDelta) throws ShenjianException {
        return this.crawlerOperation.changeCrawlerNode(appId, nodeDelta);
    }

    @Override
    public CrawlerSource getCrawlerSource(int appId) throws ShenjianException {
        return this.crawlerOperation.getCrawlerSource(appId);
    }

    @Override
    public void configCrawlerProxy(int appId, ProxyType proxyType) throws ShenjianException {
        this.crawlerOperation.configCrawlerProxy(appId, proxyType);
    }

    @Override
    public void configCrawlerHost(int appId, HostType hostType, int fileTypeFlag) throws ShenjianException {
        this.crawlerOperation.configCrawlerHost(appId, hostType, fileTypeFlag);
    }

    @Override
    public Webhook getWebhookInfo(int appId) throws ShenjianException {
        return this.crawlerOperation.getWebhookInfo(appId);
    }

    @Override
    public void deleteCrawlerWebhook(int appId) throws ShenjianException {
        this.crawlerOperation.deleteCrawlerWebhook(appId);
    }

    @Override
    public void configWebhookInfo(int appId, String url, int eventFlag) throws ShenjianException {
        this.crawlerOperation.configWebhookInfo(appId, url, eventFlag);
    }

    @Override
    public AutoPublishStatus getAutoPublishStatus(int appId) throws ShenjianException {
        return this.crawlerOperation.getAutoPublishStatus(appId);
    }

    @Override
    public void startAutoPublish(int appId, int[] publishId) throws ShenjianException {
        this.crawlerOperation.startAutoPublish(appId, publishId);
    }

    @Override
    public void stopAutoPublish(int appId) throws ShenjianException {
        this.crawlerOperation.stopAutoPublish(appId);
    }
}
