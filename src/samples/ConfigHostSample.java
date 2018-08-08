import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.FileType;
import io.shenjian.sdk.model.HostType;

public class ConfigHostSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int crawlerId = 867247;
            HostType hostType = HostType.SHENJIANSHOU;
            /* 根据需要选取要托管的文件类型 */
            int fileTypeFlag = FileType.IMAGE | FileType.VIDEO | FileType.TEXT;
            client.configCrawlerHost(crawlerId, hostType, fileTypeFlag);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
