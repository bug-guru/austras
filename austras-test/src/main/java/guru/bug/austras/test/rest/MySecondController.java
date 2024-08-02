/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.rest;

import guru.bug.austras.rest.BodyParam;
import guru.bug.austras.rest.Endpoint;
import guru.bug.austras.rest.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class MySecondController {
    private static final Logger log = LoggerFactory.getLogger(MySecondController.class);

    @Endpoint(method = "POST", path = "/sec/1")
    public void doSimplePost(@BodyParam MyDataObject obj) {
        log.info("POST Simple endpoint {}", obj);
    }

    @Endpoint(method = "POST", path = "/sec/2/{param1}")
    public void doSimplePostWithPathParam1(@PathParam String param1) {
        log.info("POST Simple endpoint with path param 1 {}", param1);
    }

    @Endpoint(method = "POST", path = "/sec/3/{param1}/x/{param2}")
    public void doSimplePostWithPathParam2(@PathParam String param1,
                                           @PathParam("param2") LocalDateTime time) {
        log.info("POST Simple endpoint with path param 2 {} {}", param1, time);
    }

    @Endpoint(method = "POST", path = "/sec/group/{groupName}")
    public MyDataObject doSimplePost3(@PathParam String groupName) {
        log.info("POST Simple endpoint with group name");
        var result = new MyDataObject();
        result.setGroup(groupName);
        result.setDate(LocalDateTime.now());
        return result;
    }

}
