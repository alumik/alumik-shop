package cn.alumik.shop.aspect;

import cn.alumik.shop.service.HistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HistoryAspect {

    private HistoryService historyService;

    public HistoryAspect(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Pointcut("execution(* cn.alumik.shop.controller.ItemController.actionDetailGetter(..))")
    private void viewItemDetail() {
    }

    @Before("viewItemDetail()")
    public void logHistory(JoinPoint joinPoint) {
        historyService.saveHistory((Integer) joinPoint.getArgs()[1]);
    }
}
