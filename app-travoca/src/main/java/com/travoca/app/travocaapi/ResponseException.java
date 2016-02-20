package com.travoca.app.travocaapi;

/**
 * @author ortal
 * @date 2015-10-02
 */
public class ResponseException extends Throwable {

    public ResponseException(String detailMessage) {
        super(detailMessage);
    }
}
