package org.studentapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/api/student")
public class StudentController {

    @PostMapping
    public ResponseEntity<String> saveStudent(){
        System.out.println("Hello");
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/stream-file-sse")
    public SseEmitter streamLargeFile() {
        System.out.println("Location ::"+System.getProperty("user.dir"));
        // 1. Create an emitter (timeout of 30 mins for large files)
        SseEmitter emitter = new SseEmitter(1800000L);

        // 2. Run the file reading in a background thread
        CompletableFuture.runAsync(() -> {
            try (Stream<String> lines = Files.lines(Paths.get("testdata.txt"))) {
                lines.forEach(line -> {
                    try {
                        // 3. Send each line as an SSE event
                        emitter.send(SseEmitter.event()
                                .name("file-line")
                                .data(line));

                        // Optional: Small sleep to simulate processing/prevent flooding
                        // Thread.sleep(10);
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                });
                emitter.complete(); // Close the connection when file ends
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
