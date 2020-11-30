package fr.uge.poo.cmdline.ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParseException extends RuntimeException {

    private final List<String> causesExceptionList;

    public ParseException(String message) {
        this.causesExceptionList =  new ArrayList<>();
        this.causesExceptionList.add(message);
    }

    public ParseException(List<String> causes) {
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
