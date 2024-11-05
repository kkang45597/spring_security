package com.mingi.ch5_3.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomInterceptor implements HandlerInterceptor {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 핸들러 메서드 호출 전에 실행될 로직
		System.out.println("현재 스레드:" + Thread.currentThread());
        System.out.println("핸들러 메서드 호출 전: " + request.getRequestURI());
        // true를 반환하면 다음 단계로 진행하고, false를 반환하면 요청 처리를 중단합니다.
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 핸들러 메서드가 호출된 후 후처리 로직
        //System.out.println("컨트롤러 메서드 실행 후 후처리: " + handler.toString());
    }
}