package com.esiran.libtokenagentj.jsonrpc;

public abstract class AbsRPCClient implements RPCClient {
    private final String url;
    protected AbsRPCClient(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
