import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.Webhook;
import io.shenjian.sdk.model.WebhookEventType;

public class WebhookSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            Webhook webhookInfo = client.getWebhookInfo(crawlerId);
            System.out.println("Webhook URL : " + webhookInfo.getUrl());
            System.out.println("Events : ");
            for (int i = 0; i < webhookInfo.getEvents().length; i++){
                System.out.println(i+1 + webhookInfo.getEvents()[i]);
            }
        } catch (ShenjianException e) {
            e.printStackTrace();
        }

        try {
            int crawlerId = 867247;
            String url = "你要设置的webhookURL";
            /* 根据需要选取webhook事件 */
            int eventFlag = WebhookEventType.DATA_NEW | WebhookEventType.DATA_UPDATED | WebhookEventType.MSG_CUSTOM;
            client.configWebhookInfo(crawlerId, url, eventFlag);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
