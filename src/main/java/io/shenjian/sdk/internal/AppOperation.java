/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.shenjian.sdk.internal;

import com.alibaba.fastjson.JSONArray;
import io.shenjian.sdk.exception.ShenjianException;
import io.shenjian.sdk.common.parser.ResponseParser;
import io.shenjian.sdk.model.App;
import io.shenjian.sdk.model.AppStatus;
import io.shenjian.sdk.model.Credentials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppOperation extends ShenjianOperation {
    public AppOperation(Credentials credentials) {
        super(credentials);
    }

    public List<App> listApp(int page, int pageSize) throws ShenjianException {
        String path = "app/list";
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(getPassportParams());
        params.put("page", String.valueOf(page));
        params.put("page_size", String.valueOf(pageSize));
        return doOperation(path, params, listAppResponseParser);
    }

    private ResponseParser<List<App>> listAppResponseParser = new ResponseParser<List<App>>() {
        @Override
        public List<App> parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                JSONArray jsonArray = (JSONArray) ((Map) data).get("list");
                List<App> list = new ArrayList<App>();
                for (Object anArrayList : jsonArray) {
                    App app = new App();
                    if (anArrayList instanceof Map) {
                        Map map = (Map) anArrayList;
                        app.setAppId((Integer) map.get("app_id"));
                        app.setName(String.valueOf(map.get("name")));
                        app.setInfo(String.valueOf(map.get("info")));
                        app.setType(String.valueOf(map.get("type")));
                        app.setStatus(AppStatus.fromValue(String.valueOf(map.get("status"))));
                        app.setCreateTime((Integer) map.get("time_create"));
                        list.add(app);
                    }
                }
                return list;
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };
}
