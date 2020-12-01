package fr.uge.poo.cmdline.ex4;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessException extends Throwable {

    private final List<String> causesExceptionList;

    public ProcessException(List<String> causes) {
        Objects.requireNonNull(causes);
        this.causesExceptionList = causes
                .stream()
                .map(message -> message + " is missing !")
                .collect(Collectors.toList());
    }

    @Override
    public String getMessage() {
        return causesExceptionList.toString();
    }
}
