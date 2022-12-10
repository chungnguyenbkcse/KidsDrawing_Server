package com.app.kidsdrawing.exception;


import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class ArtAgeNotDeleteException extends CustomGlobalException {
    public ArtAgeNotDeleteException(String message) {
        super(message, GlobalErrorCode.ERROR_NOT_DELETED);
    }
}
