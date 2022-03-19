package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;

public interface ERC721TokenCaller {
    /**
     * 获取通证 URI
     * @return URI 地址
     */
    String getTokenUri() throws Exception;

    /**
     * 转让通证的所有权
     * @param toAddress 目标地址
     * @param privateKey 转移发起私钥
     * @return 交易 HASH
     */
    Hash transfer(Address toAddress, byte[] privateKey) throws Exception;

    /**
     * 授予目标地址转移权限
     * @param address 目标地址
     * @param privateKey 持有人私钥
     * @return 交易 HASH
     */
    Hash approve(Address address, byte[] privateKey)  throws Exception;
}
