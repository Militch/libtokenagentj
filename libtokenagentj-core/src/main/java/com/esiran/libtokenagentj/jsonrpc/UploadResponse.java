package com.esiran.libtokenagentj.jsonrpc;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class UploadResponse {
    private Integer code;
    private String msg;
    private JsonElement data;
}
