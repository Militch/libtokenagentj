package com.esiran.libtokenagentj.service.params;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenTransferRequest extends TokenCallerRequest {
    @SerializedName("from_address")
    private String fromAddress;
    @SerializedName("to_address")
    private String toAddress;
}
