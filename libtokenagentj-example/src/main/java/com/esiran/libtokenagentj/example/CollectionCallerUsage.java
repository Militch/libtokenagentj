package com.esiran.libtokenagentj.example;

import com.esiran.libtokenagentj.caller.CollectionCallerParams;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.core.CollectionCaller;
import com.esiran.libtokenagentj.core.TokenAgentClient;
import com.esiran.libtokenagentj.jsonrpc.HTTPClient;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.params.NFTMintParam;
import com.esiran.libtokenagentj.util.BytesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 合集调用器
 * 此示例演示调用合集的功能
 */
public class CollectionCallerUsage {
    private static final Logger log = LoggerFactory.getLogger(CollectionCallerUsage.class);
    private static final SimpleKeyItem[] keyStorage = new SimpleKeyItem[]{
            SimpleKeyItem.createSimpleKeyItemOfHex("9a965d17bbb921951c681f37b5301b2d50cb8ed4185fb284cd19962357f6c8ac"),
    };
    private static BigInteger randomBigInt(){
        SecureRandom random = new SecureRandom();
        byte[] buf = new byte[32];
        random.nextBytes(buf);
        String hex = BytesUtil.bytes2hex(buf);
        return new BigInteger(hex, 16);
    }
    public static void main(String[] args) throws Exception {
        // 配置客户端
        RPCClient rpcClient = new HTTPClient(CommonParams.API_URL);
        TokenAgentClient tokenAgentClient = new TokenAgentClient(rpcClient,
                CommonParams.DEFAULT_BLOCKCHAIN);
        // 调用器参数设置
        CollectionCallerParams params = new CollectionCallerParams();
        // 合约地址
        String addressHex = "0xd0fed130c141ba205912da72295661042258c882";
        Address contractAddress = Address.ofHex(addressHex);
        params.setAddress(contractAddress);
        // 构造调用器
        CollectionCaller collectionCaller = tokenAgentClient.newCollection(params);
        // 以下是标准化属性获取
        String name = collectionCaller.getName();
        String symbol = collectionCaller.getSymbol();
        log.info("Collection name: {}, from contract address: {}", name, contractAddress.toHexString(true));
        log.info("Collection symbol: {}, from contract address: {}", symbol, contractAddress.toHexString(true));
        log.debug("Mite a token from contract address: {}", contractAddress.toHexString(true));
        String filename = "example.gif";
//        URL url = ClassLoader.getSystemResource(filename);
//        File file = new File(url.toURI());
//        String fileCid = tokenAgentClient.uploadFileToIPFS(file);
        NFTMintParam nftMintParam = new NFTMintParam();
        BigInteger tokenId = randomBigInt();
        nftMintParam.setTokenId(tokenId);
        nftMintParam.setTokenUri(UUID.randomUUID().toString());
        Hash txHash = collectionCaller.mintToken(nftMintParam, keyStorage[0].getPrivateKey());
        log.info("Successfully create token: {}, wait for mint: TxHash={}", tokenId, txHash.toHexString(true));
    }
}
