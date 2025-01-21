package com.future.module.agent.aop;

import com.future.common.util.UserProvider;
import com.future.module.agent.annotation.Queued;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class QueuedAspect {

    @Value("${enable.queue:false}")
    private boolean enableQueue;

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Lock>> userLocks = new ConcurrentHashMap<>();

    @Around("@annotation(queued)")
    public Object around(ProceedingJoinPoint joinPoint, Queued queued) throws Throwable {
        if (!enableQueue) {
            return joinPoint.proceed(); // 如果队列未启用，则直接调用方法
        }
        String userId = UserProvider.getUser().getUserId();
        ConcurrentHashMap<String, Lock> methodLocks = userLocks.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        String methodName = joinPoint.getSignature().toShortString(); // 获取方法签名，这里可以根据需要调整
        Lock lock = methodLocks.computeIfAbsent(methodName, k -> new ReentrantLock());
        lock.lock();
        try {
            return joinPoint.proceed(); // 调用方法并返回结果
        } finally {
            lock.unlock(); // 确保无论如何都会释放锁
        }
    }
}