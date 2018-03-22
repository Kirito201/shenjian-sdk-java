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

package io.shenjian.sdk.common.parser;

import io.shenjian.sdk.ShenjianException;
import io.shenjian.sdk.internal.ResponseMessage;

public interface ResponseParser<T> {

    /**
     * 将responseMessage转换为所需的java对象 .
     * @param responseMessage 请求结果
     * @return responseMessage所需的Java Type T对象
     * @throws ShenjianException 无法解析responseMessage.
     */
    public T parse(ResponseMessage responseMessage) throws ShenjianException;
}
