package com.esiran.libtokenagentj.jsonrpc;

public class ResponseResult<T> {
    private String jsonrpc;
    private int id;
    private ErrorMsg error;
    private T result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorMsg getError() {
        return error;
    }

    public ResponseResult<T> setError(ErrorMsg error) {
        this.error = error;
        return this;
    }
}
