package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Address;
import lombok.Data;

import java.math.BigInteger;

@Data
public class TokenCallerParams {
    /**
     * 合约地址
     */
    private Address contractAddress;
    /**
     * 通证 ID
     */
    private BigInteger tokenId;
}
