package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.caller.ERC20TokenCallerParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ERC20TokenTransferRequest extends ContractCallRequest {
    @SerializedName("from_address")
    private String fromAddress;
    @SerializedName("to_address")
    private String toAddress;
    @SerializedName("amount")
    private String amount;
}
