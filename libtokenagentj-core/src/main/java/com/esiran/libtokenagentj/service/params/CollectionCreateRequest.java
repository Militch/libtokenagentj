package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.common.BasePreSendParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建合约-请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectionCreateRequest extends BasePreSendParams {

    /**
     * 合集名称
     */
    @SerializedName("name")
    private String name;
    /**
     * 合集符号
     */
    @SerializedName("symbol")
    private String symbol;
    /**
     * 签名地址
     */
    @SerializedName("signeraddress")
    private String signerAddress;
    /**
     * 合约
     */
    @SerializedName("contracturi")
    private String contractUri;

    /**
     * 藏品 URI 前缀
     */
    @SerializedName("tokenuriprefix")
    private String tokenUriPrefix;
}
