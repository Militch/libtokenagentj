package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.common.BasePreSendParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 藏品铸造请求对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NFTMintRequestParams extends BasePreSendParams {
    @SerializedName("to_address")
    private String toAddress;
    @SerializedName("token_id")
    private String tokenId;
    @SerializedName("token_uri")
    private String tokenUri;
}
