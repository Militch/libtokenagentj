package com.esiran.libtokenagentj.common;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TXHashResp {
    @SerializedName("txhash")
    private String hash;
}
