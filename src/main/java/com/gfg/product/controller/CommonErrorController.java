package com.gfg.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
public class CommonErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Value("${application.debug.mode.enabled}")
    private boolean includeStackTrace;

    private ErrorAttributes errorAttributes;

    public CommonErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> error(HttpServletRequest request, HttpServletResponse response) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);

        log.error("Request Processing Error:" +
                errorAttributes.getErrorAttributes(servletWebRequest, true)
        );

        return new ResponseEntity<>(
                errorAttributes.getErrorAttributes(servletWebRequest, includeStackTrace),
                HttpStatus.valueOf(response.getStatus()));
    }
}
