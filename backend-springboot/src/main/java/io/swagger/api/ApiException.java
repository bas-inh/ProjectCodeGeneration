package io.swagger.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-14T10:32:11.943Z[GMT]")
public class ApiException extends Exception {
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
