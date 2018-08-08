import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.*;

public class StartCrawlerSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            int node = 1;
            CrawlerTimer crawlerTimer = new CrawlerTimer();
            /* 不同的启动模式和定时模式需要相应的套餐等级，详见开发文档与套餐介绍 */
            /* 设置启动的模式 */
            crawlerTimer.setCrawlerMode(true, true, DupType.CHANGE, ChangeType.INSERT);
            /* 设置定时的模式*/
            /* 单次定时启动 */
            crawlerTimer.setTypeOnce("2018-1-30", "18:20","19:20" );
            /* 按日定时启动 */
            crawlerTimer.setTypeDaily("2018-1-30", "2018-2-30", "18:20","19:20" );
            /* 按周定时启动 */
            crawlerTimer.setTypeWeekly("2018-1-30","2018-2-30", new int[]{1,2,3,4,5,6,7},"18:20","19:20" );
            /* 实时启动 */
            crawlerTimer.setTypeCyclically("2018-1-30", "2018-2-30", Duration.TEN_MIN, Interval.ONE_HOUR);
            client.startCrawler(crawlerId, node, crawlerTimer);
            client.startCrawler(crawlerId, crawlerTimer);
            client.startCrawler(crawlerId, node);
            client.startCrawler(crawlerId);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            client.pauseCrawler(crawlerId);
        } catch(ShenjianException e){
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            client.resumeCrawler(crawlerId);
        } catch(ShenjianException e){
            e.printStackTrace();
        }

        try{
            int crawlerId = 867247;
            client.stopCrawler(crawlerId);
        } catch(ShenjianException e){
            e.printStackTrace();
        }
    }
}
