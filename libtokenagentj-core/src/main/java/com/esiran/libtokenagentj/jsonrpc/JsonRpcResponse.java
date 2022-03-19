package com.esiran.libtokenagentj.jsonrpc;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class JsonRpcResponse {
    private String jsonrpc;
    private int id;
    private ErrorMsg error;
    private JsonElement result;
}
