package com.esiran.libtokenagentj.service.params;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenCallerRequest {
    @SerializedName("blockchain")
    private String blockchain;
    @SerializedName("contract_address")
    private String contractAddress;
    @SerializedName("token_id")
    private String tokenId;
}
