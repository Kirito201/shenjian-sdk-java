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

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class CrawlerTimer {

    protected boolean followNew = true;
    protected boolean followChange = false;
    protected DupType dupType = DupType.SKIP;
    protected ChangeType changeType = null;
    protected String timerType = "once";
    protected String onceDateStart = null;
    protected String timeStart = null;
    protected String timeEnd = null;
    protected String dateStart = null;
    protected String dateEnd = null;
    protected int[] weeklyDay = null;
    protected Duration duration = null;
    protected Interval realtimeInterval = Interval.NONE_INTERVAL;

    public final static CrawlerTimer INSTANCE = new CrawlerTimer(true, false, DupType.SKIP, null, "once", null, null, null, null, null, null, null, Interval.NONE_INTERVAL);

    public CrawlerTimer() {

    }

    private CrawlerTimer(boolean followNew, boolean followChange, DupType dupType, ChangeType changeType, String timerType, String onceDateStart, String timeStart, String timeEnd, String dateStart, String dateEnd, int[] weeklyDay, Duration duration, Interval realtimeInterval) {
        this.followNew = followNew;
        this.followChange = followChange;
        this.dupType = dupType;
        this.changeType = changeType;
        this.timerType = timerType;
        this.onceDateStart = onceDateStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weeklyDay = weeklyDay;
        this.duration = duration;
        this.realtimeInterval = realtimeInterval;
    }

    public void setCrawlerMode(boolean followNew, DupType dupType, ChangeType changeType) {

        this.setCrawlerMode(followNew, false, dupType, changeType);

    }

    public void setCrawlerMode(boolean followNew, boolean followChange, DupType dupType, ChangeType changeType) {

        this.followNew = followNew;
        this.followChange = followChange;
        this.dupType = dupType;
        this.changeType = changeType;

    }

    public void setTypeOnce(String onceDateStart, String timeStart) {

        this.setTypeOnce(onceDateStart, timeStart, null);

    }

    public void setTypeOnce(String onceDateStart, String timeStart, String timeEnd) {

        this.timerType = "once";
        this.onceDateStart = onceDateStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;

    }

    public void setTypeDaily(String timeStart) {

        this.setTypeDaily(null, null, timeStart, null);

    }

    public void setTypeDaily(String timeStart, String timeEnd) {

        this.setTypeDaily(null, null, timeStart, timeEnd);

    }

    public void setTypeDaily(String dateStart, String dateEnd, String timeStart, String timeEnd) {

        this.timerType = "daily";
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;

    }

    public void setTypeWeekly(String timeStart) {

        this.setTypeWeekly(null, null, new int[]{1, 2, 3, 4, 5, 6, 7}, timeStart, null);

    }

    public void setTypeWeekly(int[] WeeklyDay, String timeStart) {

        this.setTypeWeekly(null, null, WeeklyDay, timeStart, null);

    }

    public void setTypeWeekly(int[] WeeklyDay, String timeStart, String timeEnd) {

        this.setTypeWeekly(null, null, WeeklyDay, timeStart, timeEnd);

    }

    public void setTypeWeekly(String dateStart, String dateEnd, int[] WeeklyDay, String timeStart, String timeEnd) {

        this.timerType = "weekly";
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weeklyDay = WeeklyDay;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;

    }

    public void setTypeCyclically(Interval realtimeInterval) {

        this.setTypeCyclically(null, null, null, realtimeInterval);

    }

    public void setTypeCyclically(Duration duration, Interval realtimeInterval) {

        this.setTypeCyclically(null, null, duration, realtimeInterval);

    }

    public void setTypeCyclically(String dateStart, String dateEnd, Interval realtimeInterval) {

        this.setTypeCyclically(dateStart, dateEnd, Duration.TEN_MIN, realtimeInterval);

    }

    public void setTypeCyclically(String dateStart, String dateEnd, Duration duration, Interval realtimeInterval) {

        this.timerType = "cyclically";
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.realtimeInterval = realtimeInterval;

    }

    public Map<String, String> build() {
        Map<String, String> timerMap = new HashMap<String, String>();
        if (followNew) {
            timerMap.put("follow_new", "true");
        }
        if (followChange) {
            timerMap.put("follow_change", "true");
        }
        timerMap.put("dup_type", dupType.getDupType());
        if (changeType != null) {
            timerMap.put("change_type", changeType.getChangeType());
        }
        if (timerType.equals("once")) {
            timerMap.put("timer_type", timerType);
            timerMap.put("once_date_start", onceDateStart);
            timerMap.put("time_start", timeStart);
            timerMap.put("time_end", timeEnd);
        } else if (timerType.equals("daily")) {
            timerMap.put("timer_type", timerType);
            timerMap.put("date_start", dateStart);
            timerMap.put("date_end", dateEnd);
            timerMap.put("time_start", timeStart);
            timerMap.put("time_end", timeEnd);
        } else if (timerType.equals("weekly")) {
            timerMap.put("timer_type", timerType);
            timerMap.put("date_start", dateStart);
            timerMap.put("date_end", dateEnd);
            timerMap.put("Weekly_day", JSON.toJSONString(weeklyDay));
            timerMap.put("time_start", timeStart);
            timerMap.put("time_end", timeEnd);
        } else if (timerType.equals("cyclically")) {
            timerMap.put("timer_type", timerType);
            timerMap.put("date_start", dateStart);
            timerMap.put("date_end", dateEnd);
            if (duration != null) {
                timerMap.put("duration", String.valueOf(duration.ordinal() + 1));
            }
            timerMap.put("realtime_interval", String.valueOf(realtimeInterval.ordinal()));
        }
        return timerMap;
    }
}
