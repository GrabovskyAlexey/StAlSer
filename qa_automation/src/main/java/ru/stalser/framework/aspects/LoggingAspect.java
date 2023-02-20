package ru.stalser.framework.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.stalser.framework.helpers.AllureHelper;
import ru.stalser.framework.helpers.AutomationException;
import ru.stalser.framework.helpers.StepsHierarchy;

import java.util.List;

import static ru.stalser.framework.helpers.SoftAssertHelper.softly;


@Aspect
public class LoggingAspect {

    @Pointcut("@annotation(org.junit.jupiter.api.Test)")
    public void withTestAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

@AfterThrowing(pointcut = "anyMethod() && withTestAnnotation()", throwing = "throwable")
    public void afterThrowing(Throwable throwable) throws Throwable {

        if (throwable instanceof AutomationException) {
            throwable = new AssertionError(throwable.getMessage());
        }

        if (!softly().errorsCollected().isEmpty()) {
            AllureHelper.err(throwable);
            List<Throwable> errorsCollected = softly().errorsCollected();
            StringBuilder sb = new StringBuilder()
                    .append("Произошло ").append(errorsCollected.size()).append(" ошибки").append(System.lineSeparator());
            for (int i = 0; i < errorsCollected.size(); i++) {
                sb.append(System.lineSeparator())
                        .append(" --Ошибка ").append(i + 1).append(" -- ").append(System.lineSeparator())
                        .append(errorsCollected.get(i).getMessage())
                        .append(System.lineSeparator());
            }
            Throwable th;
            if (throwable instanceof Exception) {
                th = new Exception(sb.toString());
            } else {
                th = new AssertionError(sb.toString());
            }
            th.setStackTrace(new StackTraceElement[0]);
            StepsHierarchy.get().setFinished(true);
            throw th;
        } else {
            throw throwable;
        }
    }
}
