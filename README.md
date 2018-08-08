# Shenjian  SDK for Java

Shenjian SDK for Java 使开发者能够更便捷地连接神箭手。 你可以通过 ***Maven*** 或者下载 [single zip file](https://github.com/ShenJianShou/shenjian-sdk-java/releases) 来使用Shenjian SDK。

## 运行环境

- Java 1.6+
- Maven

## 安装

推荐使用Maven来将 Shenjian SDK for java 导入到你的项目中。导入方法如下：

```
<dependency>
  <groupId>io.shenjian.sdk</groupId>
  <artifactId>java-sdk</artifactId>
  <version>1.0.3</version>
</dependency>
```

## 使用方法

### 例子
```Java
...
    ShenjianClient client = new ShenjianClient(userKey, userSecret);
    /* 启动爬虫 */
    try {
        client.startCrawler(appId, node);
    } catch (ShenjianException e) {
        e.printStackTrace();
    }
    /* 获取爬虫状态 */
    try {
        AppStatus appStatus = client.getCrawlerStatus(appId);
        System.out.println(appStatus);
    } catch (ShenjianException e) {
        e.printStackTrace();
    }
    /* 停止爬虫 */
    try {
        client.stopCrawler(appId);
    } catch (ShenjianException e) {
        e.printStackTrace();
    }
...
```

## 作者

- 黄祖源

## 代码许可

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)