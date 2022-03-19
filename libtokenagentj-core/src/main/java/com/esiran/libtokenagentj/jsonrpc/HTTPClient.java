package com.esiran.libtokenagentj.jsonrpc;

import com.esiran.libtokenagentj.exception.RPCRequestException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HTTPClient extends AbsRPCClient {
    private static final Logger log = LoggerFactory.getLogger(HTTPClient.class);
    private static final OkHttpClient okc = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES)
            .build();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.get("application/json; charset=utf-8");
    private static final String JSON_RPC_VERSION = "2.0";
    private static final Gson g = new Gson().newBuilder().create();
    private static final ModelMapper mp = new ModelMapper();
    static {
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public HTTPClient(String apiurl){
        super(apiurl);
    }

    private <D> String request(String method, D data) throws Exception{
        RequestParams<D> params = new RequestParams<>();
        params.setId(1);
        params.setJsonrpc(JSON_RPC_VERSION);
        params.setMethod(method);
        params.setParams(data);
        String jsonParams = g.toJson(params);
        log.debug("==>RPC Request: {}", jsonParams);
        RequestBody body = RequestBody.create(jsonParams, MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                .url(getUrl())
                .post(body)
                .build();
        return exec(request);
    }

    private String exec(Request request) throws Exception{
        try (Response response = okc.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            ResponseBody rb = response.body();
            if (rb == null){
                throw new Exception("response.body == null");
            }
            String bodystring = rb.string();
            log.debug("<==RPC Response: {}", bodystring);
            return bodystring;
        }catch (Exception e) {
            log.debug("RPC Request failed: {}", e.getMessage());
            throw e;
        }
    }
    @Override
    public <T, D> T call(String method, D data, Class<T> tclass) throws Exception {
        String body = request(method, data);
        if (StringUtils.isEmpty(body)){
            throw new Exception("response.body.string is empty");
        }
        JsonRpcResponse response = g.fromJson(body, JsonRpcResponse.class);
        if (response.getError() != null){
            ErrorMsg errorMsg = response.getError();
            throw new RPCRequestException(errorMsg.toString());
        }
        JsonElement je = response.getResult();
        return g.fromJson(g.toJson(je), tclass);
    }

    @Override
    public <T, D> List<T> callList(String method, D data) throws Exception {
        String body = request(method, data);
        if(StringUtils.isEmpty(body)){
            throw new Exception("response.body.string is empty");
        }
        ResponseResult<List<String>> r = g.fromJson(body,
                new TypeToken<ResponseResult<List<String>>>() {}.getType());
        List<String> list = r.getResult();
        return g.fromJson(g.toJson(list),  new TypeToken<List<T>>() {}.getType());
    }

    @Override
    public Response proxy(String body) throws IOException {
        RequestBody rqBody = RequestBody.create(body, MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                .url(getUrl())
                .post(rqBody)
                .build();
        return okc.newCall(request).execute();
    }

    @Override
    public <T> T upload(File file, String mediaType, Class<T> tclass) throws Exception {
        if (file == null || !file.isFile() || !file.canRead()){
            throw new Exception("file cannot be read");
        }
        byte[] data = IOUtils.toByteArray(new FileInputStream(file));
        MediaType mt = MediaType.parse(mediaType);
        if (mt == null){
            throw new Exception("cannot parse media type");
        }
        log.debug("==>RPC Request upload file: {}, MediaType={}", file.getName(), mt);
        RequestBody requestBody = RequestBody.create(data, mt);
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        Request request = new Request.Builder()
                .url(String.format("%s/api/publish2ipfs", getUrl()))
                .post(body)
                .build();
        String resultBody = exec(request);
        if (StringUtils.isEmpty(resultBody)){
            throw new Exception("response.body.string is empty");
        }
        UploadResponse response = g.fromJson(resultBody, UploadResponse.class);
        if (response.getCode() == null || !response.getCode().equals(0)){
            if (response.getMsg() != null){
                throw new RPCRequestException(response.getMsg());
            }
            throw new RPCRequestException("Unknown err");
        }
        JsonElement je = response.getData();
        return g.fromJson(g.toJson(je), tclass);
    }
}
