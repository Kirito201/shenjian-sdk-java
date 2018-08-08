import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.*;

public class VariousCrawlerSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            AppStatus crawlerStatus = client.getCrawlerStatus(crawlerId);
            System.out.println("Crawler status : " + crawlerStatus.name());
        } catch (ShenjianException e) {
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            float crawlerSpeed = client.getCrawlerSpeed(crawlerId);
            System.out.println("Crawler speed = " + crawlerSpeed);
        } catch(ShenjianException e){
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            int nodeDelta = 1;
            CrawlerNodeInfo crawlerNodeInfo = client.changeCrawlerNode(crawlerId, nodeDelta);
            System.out.println("Running nodes = " + crawlerNodeInfo.getRunningNodes());
            System.out.println("Left nodes = " + crawlerNodeInfo.getLeftNodes());
        } catch(ShenjianException e){
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            CrawlerSource crawlerSource = client.getCrawlerSource(crawlerId);
            System.out.println("Crawler ID : " + crawlerSource.getAppId());
            System.out.println("Type : " + crawlerSource.getType());
            System.out.println("Count = " + crawlerSource.getCount());
        } catch(ShenjianException e){
            e.printStackTrace();
        }
    }
}
