/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.rest;

import java.time.LocalDateTime;

public class MyDataObject {
    private String group;
    private LocalDateTime date;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MyDataObject{" +
                "group='" + group + '\'' +
                ", date=" + date +
                '}';
    }
}
