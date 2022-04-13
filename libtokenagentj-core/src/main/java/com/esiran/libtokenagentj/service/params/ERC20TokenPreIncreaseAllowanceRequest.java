package com.esiran.libtokenagentj.service.params;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ERC20TokenPreIncreaseAllowanceRequest extends ContractCallRequest {
    @SerializedName("from_address")
    private String fromAddress;
    @SerializedName("spender_address")
    private String spenderAddress;
    @SerializedName("value")
    private String value;
}
