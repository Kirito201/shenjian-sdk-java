import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.ShenjianException;

import java.util.HashMap;
import java.util.Map;

public class ConfigCrawlerCustomSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            /*for example*/
            /* 你的自定义项的map集合 */
            Map<String, Object> configMap = new HashMap<String, Object>();
            configMap.put("crawlerStore", true);
            configMap.put("pageNum", 10);
            configMap.put("productUrl", "https://item.jd.com/3724805.html");
            configMap.put("keywords", new String[]{"男装","女装"});
            client.configCrawlerCustom(crawlerId, configMap);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
