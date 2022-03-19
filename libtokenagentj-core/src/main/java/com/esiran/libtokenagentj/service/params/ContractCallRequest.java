package com.esiran.libtokenagentj.service.params;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ContractCallRequest {
    @SerializedName("blockchain")
    private String blockchain;
    @SerializedName("contract_address")
    private String contractAddress;
}
