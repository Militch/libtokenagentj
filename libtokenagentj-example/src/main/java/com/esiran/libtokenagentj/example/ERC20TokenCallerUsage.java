package com.esiran.libtokenagentj.example;

import com.esiran.libtokenagentj.caller.ERC20TokenCallerParams;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.core.*;
import com.esiran.libtokenagentj.jsonrpc.HTTPClient;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;


/**
 * ERC20代币调用器使用示例
 * 此示例演示创建合集流程
 */
public class ERC20TokenCallerUsage {
    private static final Logger log = LoggerFactory.getLogger(ERC20TokenCallerUsage.class);
    private static final SimpleKeyItem[] keyStorage = new SimpleKeyItem[]{
            SimpleKeyItem.createSimpleKeyItemOfHex(
                    "9a965d17bbb921951c681f37b5301b2d50cb8ed4185fb284cd19962357f6c8ac"
            ),
            SimpleKeyItem.createRandomKeyItem(),
            SimpleKeyItem.createRandomKeyItem(),
    };

    public static void main(String[] args) throws Exception {
        // 配置客户端
        RPCClient rpcClient = new HTTPClient(CommonParams.API_URL);
        TokenAgentClient tokenAgentClient = new TokenAgentClient(rpcClient,
                CommonParams.DEFAULT_BLOCKCHAIN);
        // 构造参数
        ERC20TokenCreateParams params = new ERC20TokenCreateParams();
        params.setName("fixcoin");
        params.setSymbol("fix");
        // 创建合约
        byte[] ownerKey = keyStorage[0].getPrivateKey();
        ERC20TokenCreateResult erc20Token = tokenAgentClient.createERC20Token(params, ownerKey);
        Hash txHash = erc20Token.getTransactionHash();
        Address contractAddress = erc20Token.getContractAddress();
        log.info("Successfully create contract: {}, wait for mint: txHash={}",
                contractAddress.toHexString(true),
                txHash.toHexString(true));
        // 模拟等待时间
        Thread.sleep(60 * 1000);
        ERC20TokenCallerParams callerParams = new ERC20TokenCallerParams();
        callerParams.setContractAddress(contractAddress);
        ERC20TokenCaller erc20TokenCaller = tokenAgentClient.newERC20Token(callerParams);
        String name = erc20TokenCaller.getName();
        String symbol = erc20TokenCaller.getSymbol();
        assert name != null && name.equals(params.getName());
        assert symbol != null && symbol.equals(params.getSymbol());
        // 发起铸造
        ERC20TokenMintParam mintParam = new ERC20TokenMintParam();
        Address toAddress = keyStorage[1].getAddress();
        mintParam.setToAddress(toAddress);
        // 设置铸造额度（单位：10^-18）
        mintParam.setAmount(BigInteger.valueOf(1000));
        Hash mintHash = erc20TokenCaller.mint(mintParam, ownerKey);
        log.info("Mint ERC20Token by: {}, wait for mint: txHash={}", toAddress.toHexString(true),
                mintHash.toHexString(true));
        // 模拟等待
        Thread.sleep(60 * 1000);
        log.info("Successfully mint ERC20 Token: ContractAddress={}", contractAddress.toHexString(true));
        // 以上示例为创建并铸造 ERC20 代币
        // 以下为业务层常见业务代码
        // 查询总供给量
        BigInteger totalSupply = erc20TokenCaller.getTotalSupply();
        assert totalSupply != null && totalSupply.equals(mintParam.getAmount());
        // 查询余额
        BigInteger balance = erc20TokenCaller.getBalance(toAddress);
        assert balance != null && balance.equals(totalSupply);
        // 转移通证
        Address address = keyStorage[2].getAddress();
        BigInteger transferAmount = BigInteger.valueOf(10);
        byte[] sendKey = keyStorage[1].getPrivateKey();
        Hash hash = erc20TokenCaller.transferFrom(address, transferAmount, sendKey);
        log.info("Transfer ERC20-Token to address: {}, fromAddress={}, Amount={}, ContractAddress={} wait for mint: txHash={}",
                toAddress.toHexString(true), address.toHexString(true), transferAmount.toString(10),
                contractAddress, hash);
        // 模拟等待
        Thread.sleep(60 * 1000);
        log.info("Finish");
    }
}
