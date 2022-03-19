package com.esiran.libtokenagentj.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockRPCClient implements RPCClient {
    private static class RequestObj {
        private final String method;
        private final Map<String,Object> requestData;
        private final Map<String, Object> responseData;
        private RequestObj(String method, Map<String, Object> requestData, Map<String, Object> responseData) {
            this.method = method;
            this.requestData = requestData;
            this.responseData = responseData;
        }
    }
    private Map<String,RequestObj> requests;
    private static final Gson g = new Gson().newBuilder().create();
    public void setJsonMock(String mockBody) {
        requests = new HashMap<>();
        List<Map<String,Map<String,Object>>> list = g.fromJson(mockBody,
                new TypeToken<List<Map<String,Map<String,Object>>>>() {}.getType());
        for (Map<String, Map<String,Object>> item : list){
            Map<String,Object> request = item.get("request");
            String method = (String) request.get("method");
            Map<String,Object> response = item.get("response");
            requests.put(method, new RequestObj(method, request, response));
        }
    }

    @Override
    public <T, D> T call(String method, D data, Class<T> tclass) throws Exception {
        if (requests == null || requests.isEmpty() || !requests.containsKey(method)){
            throw new Exception("Notfound method");
        }
        RequestObj request = requests.get(method);
        Map<String,Object> rqobj = request.requestData;
        if (rqobj == null){
            throw new Exception("request err");
        }
        String jsonrpc = (String) rqobj.get("jsonrpc");
        if (StringUtils.isEmpty(jsonrpc) || !jsonrpc.equals("2.0")){
            throw new Exception("request err");
        }
        Double requestId = (Double) rqobj.get("id");
        if (requestId == null || !requestId.equals(1.0)){
            throw new Exception("request err");
        }
        Map<String,Object> response = request.responseData;
        if (response == null){
            throw new Exception("request err");
        }
        Double responseId = (Double) response.get("id");
        if (responseId == null || !responseId.equals(requestId)){
            throw new Exception("request err");
        }
        if (response.get("result") == null){
            return null;
        }
        return g.fromJson(g.toJson(response.get("result")), tclass);
    }

    @Override
    public <T, D> List<T> callList(String method, D data) throws Exception {
        return null;
    }

    @Override
    public Response proxy(String body) throws IOException {
        return null;
    }

    @Override
    public <T> T upload(File file, String mediaType, Class<T> tclass) throws Exception {
        return null;
    }
}
