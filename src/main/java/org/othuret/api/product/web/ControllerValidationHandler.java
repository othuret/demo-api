package org.othuret.api.product.web;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@ControllerAdvice
@Slf4j
public class ControllerValidationHandler {
    @ExceptionHandler(value = HttpStatusCodeException.class)
    @ResponseBody
    public void handleHttpException(HttpStatusCodeException e, HttpServletResponse response) throws IOException {
        response.sendError(e.getStatusCode().value(), e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public void handleException(RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(resolveAnnotatedResponseStatus(e).value(), e.getMessage());
    }

    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return exceptionTranslation(exception);
    }

    private HttpStatus exceptionTranslation(Exception exception) {
        if (exception instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN;
        }
        if (ExceptionUtils.indexOfThrowable(exception, IllegalArgumentException.class) != -1) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
