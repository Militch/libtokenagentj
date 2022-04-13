package com.esiran.libtokenagentj.service.params;

import com.esiran.libtokenagentj.common.BasePreSendParams;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代币创建请求结构
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ERC20TokenCreateRequest extends BasePreSendParams {
    /**
     * 通证名称
     */
    @SerializedName("name")
    private String name;

    /**
     * 通证符号
     */
    @SerializedName("symbol")
    private String symbol;
}
