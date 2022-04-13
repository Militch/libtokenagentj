package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.common.Address;
import lombok.Data;

import java.math.BigInteger;

@Data
public class ERC20TokenMintParam {
    /**
     * 铸造地址
     */
    private Address toAddress;
    /**
     * 铸造额度
     */
    private BigInteger amount;
}
