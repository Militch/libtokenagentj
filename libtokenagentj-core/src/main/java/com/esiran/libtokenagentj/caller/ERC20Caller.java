package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Hash;

public interface ERC20Caller {
    String getName() throws Exception;
    String getSymbol() throws Exception;
    Hash mintCreate();
}
