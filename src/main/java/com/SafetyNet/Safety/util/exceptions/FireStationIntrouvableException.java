package com.SafetyNet.Safety.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FireStationIntrouvableException extends RuntimeException {

    public FireStationIntrouvableException(String s) {
        super(s);
    }
}
