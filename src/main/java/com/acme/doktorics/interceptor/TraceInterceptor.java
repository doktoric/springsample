package com.acme.doktorics.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.stereotype.Component;

@Component
public class TraceInterceptor extends CustomizableTraceInterceptor {

    private static final long serialVersionUID = 2788449047173805113L;

    
    public TraceInterceptor() {
        setEnterMessage("Entering $[targetClassShortName].$[methodName]($[arguments])");
        setExitMessage("Leaving $[targetClassShortName].$[methodName](): $[returnValue] - execution took $[invocationTime] ms");
    }

    @Override
    protected void writeToLog(Log logger, String message, Throwable ex) {
        if (ex == null) {
            logger.info(message);
        } else {
            logger.info(message, ex);
        }
    }

    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return true;
    }
    
    
}
