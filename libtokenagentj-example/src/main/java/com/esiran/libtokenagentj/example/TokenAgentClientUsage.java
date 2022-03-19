package com.esiran.libtokenagentj.example;

import com.esiran.libtokenagentj.core.CollectionCreateParams;
import com.esiran.libtokenagentj.core.CollectionCreateResult;
import com.esiran.libtokenagentj.core.TokenAgentClient;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.jsonrpc.HTTPClient;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 合集创建器
 * 此示例演示创建合集流程
 */
public class TokenAgentClientUsage {
    private static final Logger log = LoggerFactory.getLogger(TokenAgentClientUsage.class);
    private static final SimpleKeyItem[] keyStorage = new SimpleKeyItem[]{
            SimpleKeyItem.createSimpleKeyItemOfHex(
                    "9a965d17bbb921951c681f37b5301b2d50cb8ed4185fb284cd19962357f6c8ac"
            ),
            SimpleKeyItem.createRandomKeyItem(),
    };

    public static void main(String[] args) throws Exception {
        // 配置客户端
        RPCClient rpcClient = new HTTPClient(CommonParams.API_URL);
        TokenAgentClient tokenAgentClient = new TokenAgentClient(rpcClient,
                CommonParams.DEFAULT_BLOCKCHAIN);
        // 构造合集参数
        CollectionCreateParams params = new CollectionCreateParams();
        params.setName("HelloWorld");
        params.setSymbol("hw");
        String contractUri = String.format("https://example.org/%s", params.getName());
        params.setContractUri(contractUri);
        params.setTokenUriPrefix("https://ipfs.io/ipfs/");
        params.setSignerAddress(keyStorage[1].getAddress().toHexString(true));
        // 执行创建合集，带入私钥进行签名
        CollectionCreateResult resp = tokenAgentClient.createCollection(
                params, keyStorage[0].getPrivateKey());
        Address contractAddress = resp.getContractAddress();
        Hash transactionHash = resp.getTransactionHash();
        log.info("Successfully create contract: address={}",contractAddress.toHexString(true));
        log.info("Contract create transaction wait for mint: {}", transactionHash.toHexString(true));
    }
}
