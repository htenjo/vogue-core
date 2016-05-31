package co.zero.vogue.resource;

import co.zero.vogue.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
public class GreetingController {
    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping("/greeting")
    public Greeting getGreeting(@RequestParam(name = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/greetingTest/{id}")
    public Greeting getGreetingTest(@PathVariable("id") long id){
        return new Greeting(id, "Name changed");
    }
}
