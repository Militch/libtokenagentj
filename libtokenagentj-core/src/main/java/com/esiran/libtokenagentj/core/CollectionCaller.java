package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.caller.ERC721Caller;
import com.esiran.libtokenagentj.caller.CollectionCallerParams;
import com.esiran.libtokenagentj.common.*;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.params.ContractCallRequest;
import com.esiran.libtokenagentj.service.params.NFTMintParam;
import com.esiran.libtokenagentj.service.params.NFTMintRequestParams;
import com.esiran.libtokenagentj.service.ChainService;
import com.esiran.libtokenagentj.service.CollectionService;
import com.esiran.libtokenagentj.service.TokenService;
import com.esiran.libtokenagentj.util.AddressUtil;

import java.math.BigInteger;

/**
 * 合集调用器
 */
public class CollectionCaller implements ERC721Caller {
    private final CollectionCallerParams callerParams;
    private final CollectionService collectionService;
    private final TokenService nftService;
    private final ChainService chainService;
    private final String blockchain;
    /**
     * 构造合集调用器
     * @param rpcClient RPC 客户端
     * @param callerParams 调用器参数
     */
    CollectionCaller(
            RPCClient rpcClient,
            String blockchain,
            CollectionCallerParams callerParams){
        this.callerParams = callerParams;
        this.blockchain = blockchain;
        collectionService = new CollectionService(rpcClient);
        nftService = new TokenService(rpcClient);
        chainService = new ChainService(rpcClient);
    }

    /**
     * 获取合集名称
     * @return 合集名称
     */
    public String getName() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        request.setContractAddress(callerParams.getAddress().toHexString(true));
        return collectionService.getCollectionName(request);
    }

    /**
     * 获取合集符号
     * @return 合集符号
     */
    @Override
    public String getSymbol() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        request.setContractAddress(callerParams.getAddress().toHexString(true));
        return collectionService.getCollectionSymbol(request);
    }

    /**
     * 获取合集拥有者
     * @return 拥有者地址
     */
    public Address getOwner(){
        return null;
    }



    /**
     * 铸造藏品
     * @param nftMintParam 参数
     * @param privateKey 铸造密钥
     * @return 交易 HASH
     */
    @Override
    public Hash mintToken(NFTMintParam nftMintParam, byte[] privateKey) throws Exception{
        if (nftMintParam == null){
            throw new IllegalArgumentException("tokenId not be null");
        }
        BigInteger tokenId = nftMintParam.getTokenId();
        if (tokenId.signum() < 0){
            throw new IllegalArgumentException("tokenId cannot be less than 0");
        }
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        Address contractAddress = callerParams.getAddress();
        NFTMintRequestParams requestParams = new NFTMintRequestParams();
        requestParams.setBlockchain(blockchain);
        requestParams.setToAddress(contractAddress.toHexString(true));
        requestParams.setFromAddress(fromAddress.toHexString(true));
        requestParams.setTokenId(nftMintParam.getTokenId().toString(10));
        requestParams.setTokenUri(nftMintParam.getTokenUri());
        CodeResp codeResp = nftService.requestPreMint(requestParams);
        return chainService.createAndSendTransaction(codeResp, blockchain,
                contractAddress.toHexString(true), privateKey);
    }
}
