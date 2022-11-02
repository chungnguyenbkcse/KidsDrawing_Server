package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class ArtTypeAlreadyCreateException extends CustomGlobalException {
    public ArtTypeAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_ART_TYPE_ALREADY_CREATE);
    }
}
