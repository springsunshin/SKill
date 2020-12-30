package com.skill.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UserException){
            String value=e.getMessage();
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("result",value);
            modelAndView.setViewName("error");
            return modelAndView;
        }else if(e instanceof ItemException){
            String value=e.getMessage();
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("result",value);
            modelAndView.setViewName("error");
            return modelAndView;
        }
        return null;
    }
}
