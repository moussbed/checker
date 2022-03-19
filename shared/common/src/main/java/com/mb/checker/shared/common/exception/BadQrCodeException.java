package com.mb.checker.shared.common.exception;

public class BadQrCodeException extends RuntimeException{

    public BadQrCodeException() {
        super("Bad QrCode provided");
    }
}
