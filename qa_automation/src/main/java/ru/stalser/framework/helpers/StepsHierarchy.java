package ru.stalser.framework.helpers;

import io.qameta.allure.model.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static io.qameta.allure.Allure.getLifecycle;

@Slf4j
public class StepsHierarchy {

    private boolean failed;
    private boolean finished;
    private static final ThreadLocal<StepsHierarchy> instance = new ThreadLocal<>();
    private final List<String> stepsHierarchy = new ArrayList<>();
    private final Map<String, Status> stepStatuses = new HashMap<>();

    public static StepsHierarchy get() {

        if (instance.get() == null) {
            instance.set(new StepsHierarchy());
        }
        return instance.get();
    }

    public boolean getFailed() {

        return failed;
    }

    public void setFailed(boolean failed) {

        this.failed = failed;
    }

    public void colourStepYellow() {

        stepStatuses.replaceAll((k, v) -> v == null || v.equals(Status.PASSED) ? Status.BROKEN : v);
    }

    public void colourStepPurple() {

        stepStatuses.replaceAll((k, v) -> v != null && v.equals(Status.PASSED) ? null : v);
    }

    public void colourStepRed() {

        failed = true;
        stepStatuses.replaceAll((k, v) -> Status.FAILED);
    }

    public void finish() {

        if (failed) {

            throw new AssertionError("В прогоне были исключения или ошибки");
        }
    }

    public void reset() {

        instance.set(null);
    }

    public void onFailStart(final String name) {

        stepStart(name, true);
    }

    public void onStepStart(final String name) {

        stepStart(name, false);
    }

    private void stepStart(final String name, boolean isFail) {

        Optional<String> uuid = getLifecycle().getCurrentTestCaseOrStep();
        if (uuid.isPresent()) {
            String stringUUID = uuid.get();
            String message = String.valueOf(new char[StepsHierarchy.get().stepsHierarchy.size()]).replace("\0", "  ") + "I-- " + name;
            stepsHierarchy.add(stringUUID);
            if (isFail) {
                log.error(message);
                stepStatuses.put(stringUUID, Status.FAILED);
            } else {
                log.info(message);
                stepStatuses.put(stringUUID, Status.PASSED);
            }
        }
    }

    public void onStepStop() {

        Optional<String> uuid = getLifecycle().getCurrentTestCaseOrStep();
        Optional<String> tc = getLifecycle().getCurrentTestCase();
        if (uuid.isPresent()) {
            String stringUUID = uuid.get();
            if (!stepsHierarchy.isEmpty()) {
                String stepUUID = stepsHierarchy.get(stepsHierarchy.size() - 1);
                if (stringUUID.equalsIgnoreCase(stepUUID)) {
                    Status status = stepStatuses.get(stepUUID);
                    if (tc.isPresent() && tc.get().equalsIgnoreCase(stringUUID)) {

                    } else {
                        getLifecycle().updateStep(stepResult -> stepResult.setStatus(status));
                    }
                    stepsHierarchy.remove(stepsHierarchy.size() - 1);
                    stepStatuses.remove(stepUUID);
                } else {
                    log.error("!!! ШАГИ НЕ РАВНЫ !!!");
                }
            } else {
                log.error("!!! СПИСОК ШАГОВ ПУСТ !!!");
            }
        } else {
            log.error("!!! ЗАКРЫВАЮ ШАГ, КОТОРОГО НЕТ !!!");
        }
    }

    public boolean getFinished() {

        return finished;
    }

    public void setFinished(boolean finished) {

        this.finished = finished;
    }
}
