import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.App;

import java.util.List;

public class AppSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            int page = 1;
            int pageSize = 50;
            List<App> list = client.listApp(page, pageSize);
            for (App aList : list) {
                System.out.println("App ID: " + aList.getAppId());
                System.out.println("Name: " + aList.getName());
                System.out.println("Info: " + aList.getInfo());
                System.out.println("Type: " + aList.getType());
                System.out.println("Status: " + aList.getStatus());
                System.out.println("Create Time: " + aList.getCreateTime());
            }
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
