import io.shenjian.sdk.ShenjianClient;
import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.model.NodeInfo;

public class UserSample {
    public static void main(String[] args) {
        String userKey = "<your user_key>";
        String userSecret = "<your user_secret>";
        ShenjianClient client = new ShenjianClient(userKey, userSecret);

        try {
            double balance = client.getBalance();
            System.out.println("balance: " + balance);
        } catch (ShenjianException e) {
            e.printStackTrace();
        }

        try {
            NodeInfo nodeInfo = client.getNodeInfo();
            System.out.println("total nodes: " + nodeInfo.getTotalNodes());
            System.out.println("running nodes: " + nodeInfo.getRunningNodes());
        } catch (ShenjianException e) {
            e.printStackTrace();
        }
    }
}
