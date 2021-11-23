package com.example.backgroundsystem.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage notFound = new ErrorPage(HttpStatus.NOT_FOUND,"/notfound");
        ErrorPage functionError = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,"/functionerror");
        ErrorPage valueError = new ErrorPage(HttpStatus.BAD_REQUEST,"/valueerror");
        registry.addErrorPages(notFound,functionError,valueError);
    }
}
