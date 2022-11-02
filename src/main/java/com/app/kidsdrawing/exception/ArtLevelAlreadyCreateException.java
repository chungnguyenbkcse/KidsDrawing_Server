package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class ArtLevelAlreadyCreateException extends CustomGlobalException {
    public ArtLevelAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_ART_LEVEL_ALREADY_CREATE);
    }
}