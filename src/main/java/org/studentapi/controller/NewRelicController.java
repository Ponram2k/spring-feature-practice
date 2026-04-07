package org.studentapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/newrelic")
public class NewRelicController {

    // Normal API
    @GetMapping("/ok")
    public String ok() {
        return "Working fine";
    }

    // SEV2 - Latency simulation
    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(5000); // 5 sec delay
        return "Slow response";
    }

    // SEV1 - Crash simulation
    @GetMapping("/crash")
    public String crash() {
        throw new RuntimeException("Simulated failure");
    }

    // SEV3 - Background delay simulation
    @GetMapping("/memory")
    public String memory() {
        List<byte[]> leak = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            leak.add(new byte[1024 * 1024]); // consume memory
        }
        return "Memory spike triggered";
    }
}
