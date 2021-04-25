package com.spring.eurekaClient;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/hi")
    public String hello(@RequestParam(required = false, defaultValue = "sb") String name) {
        System.out.println("name:" + name);
        return "hello " + (StringUtils.isNotBlank(name) ? name : "") + ",i am from port:" + port;
    }

    @RequestMapping(value = "/nmhi", params = {
            "personId=2"
            //多个条件的关系是且 不是或  如 personId = 2 ,personId = 3 要求参数personId必须=2且 personId=3 就无法调用
    })
    public String hi(@RequestParam("personId") String id, @RequestParam(required = false, defaultValue = "sb") String name) {
        System.out.println("id:" + id);
        System.out.println("name:" + name);
        return "hi " + (StringUtils.isNotBlank(name) ? name : "") + ",i am from port:" + port;
    }

    //方法 getDynamicUriValue() 会在发起到 localhost:8080/home/fetch/10 的请求时执行。
    //这里 getDynamicUriValue() 方法 id 参数也会动态地被填充为 10 这个值。
    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    String getDynamicUriValue(@PathVariable String id) {
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched";
    }
/*
    //@GetMapping
    //@PostMapping
    //@PutMapping
    //@DeleteMapping
    //@PatchMapping
    @GetMapping (value = "/fetch/{id}") //GetMapping  = RequestMapping (method = RequestMethod.GET)
    String getMapping(@PathVariable String id) {
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched";
    }
*/
    //方法 getDynamicUriValueRegex() 会在发起到 localhost:8080/home/fetch/category/shirt 的请求时执行。
    // 不过，如果发起的请求是 /home/fetch/10/shirt 的话，会抛出异常，因为这个URI并不能匹配正则表达式。
    @RequestMapping(value = "/fetch/{id:[a-z]+}/{name}", method = RequestMethod.GET)
    String getDynamicUriValueRegex(@PathVariable("name") String name,@PathVariable("id") String id) {
        System.out.println("Name is " + name);
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched using regex";
    }


    @RequestMapping("/goodbye")
    public String home() {
        return "goodbye ,i am from port:" + port;
    }
}
