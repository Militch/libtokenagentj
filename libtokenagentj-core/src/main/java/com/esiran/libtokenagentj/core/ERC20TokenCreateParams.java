package com.esiran.libtokenagentj.core;

import lombok.Data;

@Data
public class ERC20TokenCreateParams {
    /**
     * 合集名称
     */
    private String name;
    /**
     * 合集符号
     */
    private String symbol;
}
