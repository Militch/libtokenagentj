package com.esiran.libtokenagentj.jsonrpc;

import lombok.Data;

@Data
public class ErrorMsg {
    private Integer code;
    private String message;
}
