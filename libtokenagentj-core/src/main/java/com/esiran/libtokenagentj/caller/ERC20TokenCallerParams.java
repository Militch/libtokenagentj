package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Address;
import lombok.Data;

@Data
public class ERC20TokenCallerParams {
    /**
     * 合约地址
     */
    private Address contractAddress;
}
