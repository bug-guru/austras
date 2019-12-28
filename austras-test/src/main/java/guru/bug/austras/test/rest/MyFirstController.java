package guru.bug.austras.test.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class MyFirstController {
    private static final Logger log = LoggerFactory.getLogger(MyFirstController.class);

//    @Endpoint(method = "POST", path = "/my/1")
    public void doSimplePost() {
        log.info("POST Simple endpoint");
    }

    //    @Endpoint(method = "POST", path = "/my/2/{param1}")
    public void doSimplePostWithPathParam1(String param1) {
        log.info("POST Simple endpoint with path param 1 {}", param1);
    }

    //    @Endpoint(method = "POST", path = "/my/3/{param1}/x/{param2}")
    public void doSimplePostWithPathParam2(String param1, LocalDateTime param2) {
        log.info("POST Simple endpoint with path param 2 {} {}", param1, param2);
    }

    //    @Endpoint(method = "POST", path = "/my/group/{groupName}")
    public MyDataObject doSimplePost3(String groupName) {
        log.info("POST Simple endpoint with group name");
        var result = new MyDataObject();
        result.setGroup(groupName);
        result.setDate(LocalDateTime.now());
        return result;
    }

}
