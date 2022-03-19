package com.esiran.libtokenagentj.common;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BasePreSendParams {
    /**
     * 区块链标识
     */
    @SerializedName("blockchain")
    private String blockchain;
    /**
     * 创建者地址
     */
    @SerializedName("from_address")
    private String fromAddress;
}
