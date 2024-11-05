package com.mingi.ch5_3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import java.io.IOException;
import java.util.concurrent.*;
@RestController
public class HelloController {
	
//  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
//    @GetMapping("/home")
//    public String home() {
//        return "home.html";
//    }
    @GetMapping("/moring")
    public void hello(HttpServletRequest request, HttpServletResponse response,
    		Authentication a) throws ServletException, IOException {
        request.getRequestDispatcher("/hello/dmove")
               .forward(request, response);
    }
    @GetMapping("/hello/dmove")
    public ResponseEntity<String> handlerForwardedRequest() {
        return ResponseEntity.ok("this is final destination after forwrding");
    }
    @GetMapping("/hello")
    @Async // 내부적으로 Executor를 사용하기 때문에 이 hello을 태스크로 executor의 submit에 제출
           // 그러므로 리턴값을 Future로 받아야 함.
           // Future도 태스크가 리턴값을 리턴했을 때 사용하는 CompletableFuture를 사용해야 함.
    public CompletableFuture<String> hello(/*Authentication a*/) {
        System.out.println("Start hello() method in thread: " + Thread.currentThread().getName());
        System.out.println("현재 스레드:" + Thread.currentThread());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();
        String name = a.getName();
        try {
            Thread.sleep(3000); // 3초 지연
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End hello() method in thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(name);
    }
    public Future<String> hello(Authentication a) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication aa = context.getAuthentication();
        String name = a.getName();
        return new AsyncResult<String>(name);
    }
    @GetMapping("/ciao")
    public String ciao(Authentication a) throws Exception {
    	Thread t1 = Thread.currentThread();
    	
        Callable<String> task = () -> {
        	Thread t2 = Thread.currentThread();
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication aa = context.getAuthentication();
            return context.getAuthentication().getName();
        	};
        ExecutorService e = Executors.newCachedThreadPool();
        try {
            var contextTask = new DelegatingSecurityContextCallable<>(task);
            return "Ciao, " + e.submit(contextTask).get() + "!";
        } finally {
            e.shutdown();
        }
    }
}