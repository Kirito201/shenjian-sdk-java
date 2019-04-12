import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;

public class EditCrawlerSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            String name = "要修改的名字";
            String info = "要修改的描述";
            client.editCrawler(crawlerId, name, info);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
