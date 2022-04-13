package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import lombok.Data;

/**
 * 创建合约-返回参数
 */
@Data
public class ERC20TokenCreateResult {
    /**
     *  交易Hash
     */
    private Hash transactionHash;
    /**
     * 合约地址
     */
    private Address contractAddress;
}
