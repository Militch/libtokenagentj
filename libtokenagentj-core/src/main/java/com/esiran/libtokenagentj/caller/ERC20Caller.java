package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.core.ERC20TokenMintParam;

import java.math.BigInteger;

public interface ERC20Caller {
    /**
     * 查询通证名称
     * @return 查询结果
     */
    String getName() throws Exception;

    /**
     * 查询通证符号
     * @return 查询结果
     */
    String getSymbol() throws Exception;

    /**
     * 查询总供给量
     * @return 查询结果
     */
    BigInteger getTotalSupply() throws Exception;

    /**
     * 铸造通证
     * @param mintParam 铸造参数
     * @param privateKey 签名私钥
     * @return 交易HASH
     */
    Hash mint(ERC20TokenMintParam mintParam, byte[] privateKey) throws Exception;

    /**
     * 查询指定地址账户余额
     * @param address 查询地址
     * @return 账户余额
     */
    BigInteger getBalance(Address address) throws Exception;

    Hash approve(Address fromAddress, );
    /**
     * 转移通证余额
     * @param fromAddress 源地址
     * @param toAddress 目的地址
     * @param amount 转移额度
     * @param privateKey 签名私钥
     * @return 交易HASH
     */
    Hash transferFrom(Address toAddress, BigInteger amount, byte[] privateKey) throws Exception;
}
