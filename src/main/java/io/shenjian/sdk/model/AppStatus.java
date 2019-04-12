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

package io.shenjian.sdk.model;

public enum AppStatus {
    INIT,
    STARTING,
    RUNNING,
    STOPPED,
    STOPPING,
    PAUSED,
    PAUSING,
    RESUMING,
    SLEEPING;

    public static AppStatus fromValue(String status) {
        if (status == null) return INIT;
        if (status.equals("init")) return INIT;
        if (status.equals("starting")) return STARTING;
        if (status.equals("running")) return RUNNING;
        if (status.equals("stopped")) return STOPPED;
        if (status.equals("stopping")) return STOPPING;
        if (status.equals("paused")) return PAUSED;
        if (status.equals("pausing")) return PAUSING;
        if (status.equals("resuming")) return RESUMING;
        if (status.equals("sleeping")) return SLEEPING;
        return INIT;
    }
}