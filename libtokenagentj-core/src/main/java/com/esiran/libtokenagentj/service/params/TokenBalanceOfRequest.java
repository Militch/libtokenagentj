package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.common.BasePreSendParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 余额查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenBalanceOfRequest extends ContractCallRequest {
    /**
     * 查询地址
     */
    @SerializedName("account")
    private String account;
}