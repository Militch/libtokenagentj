package com.esiran.libtokenagentj.example;

import com.esiran.libtokenagentj.caller.CollectionCallerParams;
import com.esiran.libtokenagentj.caller.TokenCallerParams;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.core.CollectionCaller;
import com.esiran.libtokenagentj.core.TokenAgentClient;
import com.esiran.libtokenagentj.core.TokenCaller;
import com.esiran.libtokenagentj.jsonrpc.HTTPClient;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class TokenCallerUsage {
    private static final Logger log = LoggerFactory.getLogger(TokenCallerUsage.class);
    private static final SimpleKeyItem[] keyStorage = new SimpleKeyItem[]{
            SimpleKeyItem.createSimpleKeyItemOfHex("9a965d17bbb921951c681f37b5301b2d50cb8ed4185fb284cd19962357f6c8ac"),
            SimpleKeyItem.createRandomKeyItem()
    };

    public static void main(String[] args) throws Exception {
        // 配置客户端
        RPCClient rpcClient = new HTTPClient(CommonParams.API_URL);
        TokenAgentClient tokenAgentClient = new TokenAgentClient(rpcClient,
                CommonParams.DEFAULT_BLOCKCHAIN);
        // 调用器参数设置
        TokenCallerParams params = new TokenCallerParams();
        // 合约地址
        String addressHex = "0xd0fed130c141ba205912da72295661042258c882";
        String tokenId = "25051374200040449536707411896171407110051408887527702313459461632648978453186";
        Address contractAddress = Address.ofHex(addressHex);
        params.setTokenId(new BigInteger(tokenId, 10));
        params.setContractAddress(contractAddress);
        // 构造调用器
        TokenCaller caller = tokenAgentClient.newToken(params);
        String tokenUri = caller.getTokenUri();
        log.info("Token uri: {}, from contract address: {}, by token id: {}", tokenUri,
                contractAddress.toHexString(true), tokenId);
        Address toAddress= keyStorage[1].getAddress();
        log.info("Transfer token to: {}, by token id: {}, from contract address: {}", toAddress.toHexString(true),
                tokenId, contractAddress.toHexString(true));
        Hash txHash = caller.transfer(toAddress, keyStorage[0].getPrivateKey());
        log.info("Successfully send transfer request wait for transfer: transaction hash={}, to: {}, by token id: {}, from contract: {}",
                txHash.toHexString(true), toAddress.toHexString(true), tokenId, contractAddress.toHexString(true));
        Thread.sleep(90 * 1000);
        Hash txHash2 = caller.approve(keyStorage[0].getAddress(), keyStorage[0].getPrivateKey());
        log.info("Successfully send transfer request wait for approve: transaction hash={}, to: {}, by token id: {}, from contract: {}",
                txHash2.toHexString(true), toAddress.toHexString(true), tokenId, contractAddress.toHexString(true));
    }
}
