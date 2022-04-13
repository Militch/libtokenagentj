package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.common.BasePreSendParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ERC20TokenPreMintRequest extends BasePreSendParams {
    @SerializedName("contract_address")
    private String contractAddress;
    @SerializedName("to_address")
    private String toAddress;
    @SerializedName("amount")
    private String amount;
}
