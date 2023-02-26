package ru.stalser.framework.aspects;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.AspectUtils;
import io.qameta.allure.util.ResultsUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import ru.stalser.framework.helpers.StepsHierarchy;

import java.util.List;
import java.util.UUID;

@Aspect
public class StepsAspectsOverride {

    private static final InheritableThreadLocal<AllureLifecycle> LIFECYCLE = new InheritableThreadLocal<>() {

        @Override
        protected AllureLifecycle initialValue() {
            return Allure.getLifecycle();
        }
    };

    @Pointcut("@annotation(io.qameta.allure.Step)")
    public void withStepAnnotation() {

    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {

    }

    @Before("anyMethod() && withStepAnnotation()")
    public void stepStart(final JoinPoint joinPoint) {

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Step step = methodSignature.getMethod().getAnnotation(Step.class);

        final String uuid = UUID.randomUUID().toString();
        final String name = AspectUtils.getName(step.value(), joinPoint);
        final List<Parameter> parameters = AspectUtils.getParameters(methodSignature, joinPoint.getArgs());

        final StepResult result = new StepResult()
                .setName(name)
                .setParameters(parameters);

        getLifecycle().startStep(uuid, result);
        StepsHierarchy.get().onStepStart(name);
    }

    @AfterThrowing(pointcut = "anyMethod() && withStepAnnotation()", throwing = "e")
    public void stepFailed(final Throwable e) {

        getLifecycle().updateStep(s -> s
                .setStatus(ResultsUtils.getStatus(e).orElse(Status.BROKEN))
                .setStatusDetails(ResultsUtils.getStatusDetails(e).orElse(null)));
        getLifecycle().stopStep();
    }

    @AfterReturning(pointcut = "anyMethod() && withStepAnnotation()")
    public void stepStop() {

        StepsHierarchy.get().onStepStop();
        getLifecycle().stopStep();
    }

    public static AllureLifecycle getLifecycle() {

        return LIFECYCLE.get();
    }
}
