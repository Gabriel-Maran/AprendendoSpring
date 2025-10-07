package br.com.gabrielmaran.AprendendoAvisos.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestLogController {
    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/test")
    public String testLog() {
        logger.debug("This is an DEBUG Log");
        logger.info("This is an INFO Log");
        logger.warn("This is a WARN Log");
        logger.error("This is an ERROR Log");
        return "Logs generated successfully!";
    }
}
