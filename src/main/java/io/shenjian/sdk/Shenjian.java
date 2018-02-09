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

import io.shenjian.sdk.model.CrawlerTimer;
import io.shenjian.sdk.model.Crawler;
import io.shenjian.sdk.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author hzy
 */
public interface Shenjian {
    //******* user 接口 *****

    /**
     * 获取账户余额
     *
     * @return 账户余额，单位元
     */
    float getBalance() throws ShenjianException;

    /**
     * 获取账户的节点使用情况
     *
     * @return 节点使用情况
     */
    NodeInfo getNodeInfo() throws ShenjianException;


    //****************** App 接口 ************************

    /**
     * 获取App列表
     *
     * @param page     获取第几页
     * @param pageSize 每页多少个
     * @return App列表
     */
    List<App> listApp(int page, int pageSize) throws ShenjianException;


    //******************** Crawler 接口 ********************

    /**
     * 获取爬虫列表
     *
     * @param page     获取第几页
     * @param pageSize 每页多少个
     * @return 爬虫列表
     */
    List<Crawler> listCrawler(int page, int pageSize) throws ShenjianException;

    /**
     * 创建爬虫
     *
     * @param name 爬虫名
     * @param info 爬虫描述
     * @param code 爬虫代码
     * @return 创建的爬虫
     */
    Crawler createCrawler(String name, String info, String code) throws ShenjianException;

    /**
     * 删除指定爬虫
     *
     * @param appId 爬虫ID
     */
    void deleteCrawler(int appId) throws ShenjianException;

    /**
     * 修改指定爬虫信息，包括爬虫名和爬虫简介
     *
     * @param appId   爬虫ID
     * @param appName 爬虫名称，不设置则不修改
     * @param appInfo 爬虫简介，不设置则不修改
     */
    void editCrawler(int appId, String appName, String appInfo) throws ShenjianException;

    /**
     * 修改爬虫的自定义设置
     *
     * @param appId     爬虫ID
     * @param configMap 自定义参数，每个爬虫可设置的自定义项不同
     *                  example boolean configMap.put("crawlerStore", true)
     *                  int configMap.put("pageNum", 10)
     *                  String configMap.put("productUrl", "https://item.jd.com/3724805.html")
     *                  Array configMap.put("keywords", new String[]{"男装","女装"})
     */
    void configCrawlerCustom(int appId, Map<String, Object> configMap) throws ShenjianException;

    /**
     * 启动指定爬虫
     *
     * @param appId        爬虫ID
     * @param node         使用节点数，不设置默认为1
     * @param crawlerTimer 定时启动爬虫的定时器{@link CrawlerTimer}，不设置则直接启动爬虫
     */
    void startCrawler(int appId, int node, CrawlerTimer crawlerTimer) throws ShenjianException;

    void startCrawler(int appId, CrawlerTimer crawlerTimer) throws ShenjianException;

    void startCrawler(int appId, int node) throws ShenjianException;

    void startCrawler(int appId) throws ShenjianException;

    /**
     * 停止指定爬虫
     *
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void stopCrawler(int appId) throws ShenjianException;

    /**
     * 暂停指定爬虫
     *
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void pauseCrawler(int appId) throws ShenjianException;

    /**
     * 恢复指定爬虫
     *
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void resumeCrawler(int appId) throws ShenjianException;

    /**
     * 获取指定爬虫状态
     *
     * @param appId 爬虫ID
     * @return 爬虫状态
     * @throws ShenjianException
     */
    AppStatus getCrawlerStatus(int appId) throws ShenjianException;

    /**
     * 获取指定爬虫运行速度
     *
     * @param appId 爬虫ID
     * @return 运行速度，单位kB/s
     * @throws ShenjianException
     */
    float getCrawlerSpeed(int appId) throws ShenjianException;

    /**
     * 增加或减少指定爬虫所使用的节点
     *
     * @param appId     爬虫ID
     * @param nodeDelta 增加或减少多少节点，大于0表示增加，小于0表示减少，不能为0
     * @return 节点使用情况
     * @throws ShenjianException
     */
    CrawlerNodeInfo changeCrawlerNode(int appId, int nodeDelta) throws ShenjianException;

    /**
     * 获取爬虫对应的数据源信息
     *
     * @param appId 爬虫ID
     * @return 数据源信息
     * @throws ShenjianException
     */
    CrawlerSource getCrawlerSource(int appId) throws ShenjianException;

    /**
     * 清空爬虫的爬取结果。
     * 注意：爬取结果清空后无法恢复，请谨慎调用
     *
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void clearCrawlerData(int appId) throws ShenjianException;

    /**
     * 删除爬虫N天前爬到的数据。
     * 注意：此接口调用后会立即返回，删除数据在后台进行。此操作不可取消，爬取结果删除后无法恢复，请谨慎调用
     *
     * @param appId 爬虫ID
     * @param days 删除N天前的数据，无默认值，N最小为1
     * @throws ShenjianException
     */
    void deleteCrawlerData(int appId, int days) throws ShenjianException;

    /**
     * 修改指定爬虫使用的代理
     *
     * @param appId     爬虫ID
     * @param proxyType 代理IP类型
     * @throws ShenjianException
     */
    void configCrawlerProxy(int appId, ProxyType proxyType) throws ShenjianException;

    /**
     * 修改指定爬虫的托管设置
     *
     * @param appId    爬虫ID
     * @param hostType 托管类型
     * @param fileTypeFlag 要托管的文件类型
     * @throws ShenjianException
     */
    void configCrawlerHost(int appId, HostType hostType, int fileTypeFlag) throws ShenjianException;

    /**
     * 获取指定爬虫的webhook设置
     *
     * @param appId 爬虫ID
     * @return webhook信息
     * @throws ShenjianException
     */
    Webhook getWebhookInfo(int appId) throws ShenjianException;

    /**
     * 删除指定爬虫的webhook设置
     *
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void deleteCrawlerWebhook(int appId) throws ShenjianException;

    /**
     * 修改指定爬虫的webhook设置
     *
     * @param appId 爬虫ID
     * @param url   webhook的通知地址，需要是能外网访问的地址
     * @param eventFlag webhook的标签设置
     * @throws ShenjianException
     */
    void configWebhookInfo(int appId, String url, int eventFlag) throws ShenjianException;

    /**
     * 获取指定爬虫的自动发布状态
     *
     * @param appId 爬虫ID
     * @return 自动发布状态
     * @throws ShenjianException
     */
    AutoPublishStatus getAutoPublishStatus(int appId) throws ShenjianException;

    /**
     * 启动指定爬虫的自动发布
     * @param appId 爬虫ID
     * @param publishId 发布项ID
     * @throws ShenjianException
     */
    void startAutoPublish(int appId, int[] publishId) throws ShenjianException;

    /**
     * 停止指定爬虫的自动发布
     * @param appId 爬虫ID
     * @throws ShenjianException
     */
    void stopAutoPublish(int appId) throws ShenjianException;
}
