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

import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.common.parser.ResponseParser;
import io.shenjian.sdk.model.Credentials;
import io.shenjian.sdk.model.NodeInfo;

import java.math.BigDecimal;
import java.util.Map;

public class UserOperation extends ShenjianOperation {
    public UserOperation(Credentials credentials) {
        super(credentials);
    }

    public float getBalance() throws ShenjianException {
        String path = "user/money";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, userBalanceResponseParser);
    }

    private ResponseParser<Float> userBalanceResponseParser = new ResponseParser<Float>() {
        @Override
        public Float parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data instanceof Map) {
                Object money = ((Map) data).get("money");
                if (money != null && !(money instanceof String)) {
                    try {
                        Float aFloat = Float.valueOf(money.toString());
                        BigDecimal bd = new BigDecimal((double)aFloat);
                        bd = bd.setScale(2,4);
                        aFloat = bd.floatValue();
                        return aFloat;
                    } catch (NumberFormatException e) {
                        throw new ShenjianException("接口返回异常", e, responseMessage);
                    }
                }
            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };

    public NodeInfo getNodeInfo() throws ShenjianException {
        String path = "user/node";
        Map<String, String> params = getPassportParams();
        return doOperation(path, params, nodeInfoResponseParser);
    }

    private ResponseParser<NodeInfo> nodeInfoResponseParser = new ResponseParser<NodeInfo>() {
        @Override
        public NodeInfo parse(ResponseMessage responseMessage) throws ShenjianException {
            Object data = responseMessage.getData();
            if (data != null && data instanceof Map) {
                NodeInfo nodeInfo = new NodeInfo();
                try {
                    nodeInfo.setTotalNodes((int) Math.ceil((Double)((Map)data).get("node_all")));
                    nodeInfo.setRunningNodes((int) Math.ceil((Double)((Map)data).get("node_running")));
                    if (((Map)data).get("node_gpu_all") != null) {
                        nodeInfo.setTotalGpuNodes((int) Math.ceil((Double)((Map)data).get("node_gpu_all")));
                    }
                    if (((Map)data).get("node_gpu_running") != null) {
                        nodeInfo.setRunningGpuNodes((int) Math.ceil((Double)((Map)data).get("node_gpu_running")));
                    }
                    return nodeInfo;
                } catch (NumberFormatException e) {
                    throw new ShenjianException("接口返回异常", e, responseMessage);
                }

            }
            throw new ShenjianException("接口返回异常", responseMessage);
        }
    };
}
