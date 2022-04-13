package com.esiran.libtokenagentj.core;


import com.esiran.libtokenagentj.caller.TokenCallerParams;
import com.esiran.libtokenagentj.caller.CollectionCallerParams;
import com.esiran.libtokenagentj.service.ERC20TokenService;
import com.esiran.libtokenagentj.service.IPFSService;
import com.esiran.libtokenagentj.service.params.CollectionCreateRequest;
import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.ChainService;
import com.esiran.libtokenagentj.service.CollectionService;
import com.esiran.libtokenagentj.service.params.ERC20TokenCreateRequest;
import com.esiran.libtokenagentj.util.AddressUtil;
import com.esiran.libtokenagentj.common.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;

public class TokenAgentClient implements ERC721AgentClient {
    private static final Logger log = LoggerFactory.getLogger(TokenAgentClient.class);
    private final CollectionService collectionService;
    private final ChainService chainService;
    private final RPCClient rpcClient;
    private final ERC20TokenService erc20TokenService;
    private final IPFSService ipfsService;
    private final String blockchain;
    public TokenAgentClient(RPCClient rpcClient, String blockchain) {
        this.rpcClient = rpcClient;
        collectionService = new CollectionService(rpcClient);
        chainService = new ChainService(rpcClient);
        ipfsService = new IPFSService(rpcClient);
        erc20TokenService = new ERC20TokenService(rpcClient);
        this.blockchain = blockchain;
    }

    /**
     * 创建合集
     * @param params 请求参数
     * @param privateKey 签名私钥
     * @return 请求结果
     */
    public CollectionCreateResult createCollection(
            CollectionCreateParams params,
            byte[] privateKey) throws Exception {
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        CollectionCreateRequest requestParams = new CollectionCreateRequest();
        requestParams.setBlockchain(blockchain);
        requestParams.setFromAddress(fromAddress.toHexString(true));
        requestParams.setName(params.getName());
        requestParams.setSymbol(params.getSymbol());
        requestParams.setSignerAddress(params.getSignerAddress());
        requestParams.setContractUri(params.getContractUri());
        requestParams.setTokenUriPrefix(params.getTokenUriPrefix());
        // 请求预创建合集，获取创建合约字节码
        CodeResp data = collectionService.requestCollectionPreCreate(requestParams);
        // 创建并发送经过签名的原始交易
        Hash txHash = chainService.createAndSendTransaction(
                data, blockchain, null, privateKey);
        CollectionCreateResult resp = new CollectionCreateResult();
        // 根据源地址+nonce生成合约地址
        Address srcAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        Address cAddress = AddressUtil.createAddress(srcAddress, data.getNonce());
        resp.setContractAddress(cAddress);
        resp.setTransactionHash(txHash);
        return resp;
    }

    /**
     * 根据合约地址实例化合集调用器
     * @param callerParams 合约地址
     * @return 调用器
     */
    public CollectionCaller newCollection(CollectionCallerParams callerParams){
        return new CollectionCaller(rpcClient, blockchain, callerParams);
    }

    public TokenCaller newToken(TokenCallerParams params){
        return new TokenCaller(rpcClient, blockchain, params);
    }

    @Override
    public ERC20TokenCreateResult createERC20Token(ERC20TokenCreateParams params, byte[] privateKey) throws Exception {
        // 构造结构体
        ERC20TokenCreateRequest erc20TokenCreateRequest = new ERC20TokenCreateRequest();
        erc20TokenCreateRequest.setBlockchain(blockchain);
        erc20TokenCreateRequest.setName(params.getName());
        erc20TokenCreateRequest.setSymbol(params.getSymbol());
        // 通过私钥解析地址
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        erc20TokenCreateRequest.setFromAddress(fromAddress.toHexString(true));
        // 请求生成合约字节码
        CodeResp codeResp = erc20TokenService.requestPreCreate(erc20TokenCreateRequest);
        // 发送交易
        Hash txHash = chainService.createAndSendTransaction(codeResp, blockchain, null, privateKey);
        Address contractAddress = AddressUtil.createAddress(fromAddress, codeResp.getNonce());
        ERC20TokenCreateResult result = new ERC20TokenCreateResult();
        result.setTransactionHash(txHash);
        result.setContractAddress(contractAddress);
        return result;
    }


    /**
     * 上传文件到IPFS
     * @param file 文件对象
     * @return IPFS CID
     */
    public String uploadFileToIPFS(File file) throws Exception {
        String contentType = Files.probeContentType(file.toPath());
        return ipfsService.upload(file, contentType);
    }
}
