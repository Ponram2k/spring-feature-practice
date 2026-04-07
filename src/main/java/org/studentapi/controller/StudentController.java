package org.studentapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/api/student")
public class StudentController {

    @PostMapping
    public ResponseEntity<String> saveStudent(@RequestHeader Map<String, String> headerMap){
        System.out.println("Hello");
        for (var headerEntity : headerMap.entrySet()){
            System.out.println(headerEntity.getKey()+"="+headerEntity.getValue());
        }

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

    @GetMapping(value = "/stream-flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> streamFile() {
        Path path = Paths.get("testdata.txt");

        return Flux.using(
                        () -> Files.lines(path),             // Open the file stream
                        lineStream -> Flux.fromStream(lineStream), // Convert to Flux
                        BaseStream::close                    // Close file when stream ends/fails
                )
                .checkpoint("File Streaming")
                .log(); // See the signals (onNext, onComplete) in your console
    }
}
