package com.esiran.libtokenagentj.core;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CollectionCreateParams {
    /**
     * 合集名称
     */
    private String name;
    /**
     * 合集符号
     */
    private String symbol;
    /**
     * 签名地址
     */
    private String signerAddress;
    /**
     * 业务访问地址
     */
    private String contractUri;

    /**
     * 藏品 URI 前缀
     */
    private String tokenUriPrefix;
}
