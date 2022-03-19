package com.esiran.libtokenagentj.common;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigInteger;

@Data
public class CodeResp {
    @SerializedName("gas_price")
    private BigInteger gasPrice;
    @SerializedName("gas_limit")
    private BigInteger gasLimit;
    @SerializedName("nonce")
    private BigInteger nonce;
    @SerializedName("code")
    private String code;
}
