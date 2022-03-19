package com.esiran.libtokenagentj.jsonrpc;

import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface RPCClient {
    <T, D> T call(String method, D data, Class<T> tclass) throws Exception;
    <T, D> List<T> callList(String method, D data) throws Exception;
    Response proxy(String body) throws IOException;
    <T> T upload(File file, String mediaType, Class<T> tclass) throws Exception;
}
