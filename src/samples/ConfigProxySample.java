import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.model.ProxyType;

public class ConfigProxySample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            /* 不同的套餐权限可选取不同的代理等级 */
            ProxyType proxyType = ProxyType.VIP;
            client.configCrawlerProxy(crawlerId, proxyType);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
