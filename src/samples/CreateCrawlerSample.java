import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.Crawler;

public class CreateCrawlerSample {
    public static void main(String[] args) {

        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);
        try {
            String name = "***爬虫的名字***";
            String info = "***爬虫的描述***";
            String code = "***爬虫代码***";
            Crawler crawler = client.createCrawler(name, info, code);
            System.out.println("Crawler ID : " + crawler.getAppId());
            System.out.println("Crawler name : " + crawler.getName());
            System.out.println("Crawler status : " + crawler.getStatus());
            System.out.println("Create time : " + crawler.getCreateTime());
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
