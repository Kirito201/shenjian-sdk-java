import io.shenjian.sdk.ShenjianApi;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.model.ApiResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiSample {
    public void foo() {
        ShenjianApi apiClient = new ShenjianApi("<your api appid>");
        Map<String, String> params = new HashMap<>();
        params.put("<param1>", "<value>");
        try {
           ApiResponse res = apiClient.callApi(params);
           String jsonData = res.getData();
           //TODO: 处理获取到的json格式数据...
        } catch (ShenjianException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
