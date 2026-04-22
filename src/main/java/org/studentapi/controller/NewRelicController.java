package org.studentapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/newrelic")
public class NewRelicController {

    // ✅ Success
    @GetMapping("/ok")
    public String ok() {
        return "Working fine";
    }

    // ✅ Latency
    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(5000);
        return "Slow response";
    }

    // ✅ Failure
    @GetMapping("/fail")
    public String fail() {
        throw new RuntimeException("Simulated failure");
    }

    // ✅ CPU Load (HPA)
    @GetMapping("/cpu-load")
    public String cpuLoad() {
        long end = System.currentTimeMillis() + 60000;
        while (System.currentTimeMillis() < end) {
            Math.sqrt(Math.random());
        }
        return "CPU load generated";
    }

    // ✅ Crash (Restart)
    @GetMapping("/crash")
    public String crash() {
        System.exit(1);
        return "This will never execute";
    }

    // ✅ OOM Test
    private static final List<byte[]> leak = new ArrayList<>();

    @GetMapping("/memory-leak")
    public String memoryLeak() {
        while (true) {
            leak.add(new byte[1024 * 1024]);
        }
    }
}