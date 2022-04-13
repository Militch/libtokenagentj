package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.caller.TokenCallerParams;
import com.esiran.libtokenagentj.caller.ERC721TokenCaller;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.ChainService;
import com.esiran.libtokenagentj.service.TokenService;
import com.esiran.libtokenagentj.service.params.TokenApproveRequest;
import com.esiran.libtokenagentj.service.params.TokenCallerRequest;
import com.esiran.libtokenagentj.service.params.TokenTransferRequest;
import com.esiran.libtokenagentj.util.AddressUtil;

import javax.print.DocFlavor;
import java.math.BigInteger;

public class TokenCaller implements ERC721TokenCaller {
    private final TokenCallerParams params;
    private final String blockchain;
    private final TokenService tokenService;
    private final ChainService chainService;
    public TokenCaller(RPCClient rpcClient, String blockchain, TokenCallerParams params) {
        this.blockchain = blockchain;
        this.params = params;
        tokenService = new TokenService(rpcClient);
        chainService = new ChainService(rpcClient);
    }

    @Override
    public String getTokenUri() throws Exception {
        TokenCallerRequest request = new TokenCallerRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = params.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        BigInteger tokenId = params.getTokenId();
        request.setTokenId(tokenId.toString(10));
        return tokenService.getTokenUri(request);
    }

    @Override
    public Hash transfer(Address toAddress, byte[] privateKey) throws Exception {
        TokenTransferRequest request = new TokenTransferRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = params.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        BigInteger tokenId = params.getTokenId();
        request.setTokenId(tokenId.toString(10));
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        request.setFromAddress(fromAddress.toHexString(true));
        request.setToAddress(toAddress.toHexString(true));
        CodeResp codeResp = tokenService.requestPreTransferFrom(request);
        if (codeResp.getGasLimit().compareTo(BigInteger.valueOf(214590)) < 0) {
            codeResp.setGasLimit(BigInteger.valueOf(214590));
        }
        return chainService.createAndSendTransaction(
                codeResp, blockchain, contractAddress.toHexString(true), privateKey);
    }

    @Override
    public Hash approve(Address address, byte[] privateKey) throws Exception {
        TokenApproveRequest request = new TokenApproveRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = params.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        BigInteger tokenId = params.getTokenId();
        request.setTokenId(tokenId.toString(10));
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        request.setFromAddress(fromAddress.toHexString(true));
        request.setToAddress(address.toHexString());
        CodeResp codeResp = tokenService.requestPreApprove(request);
        if (codeResp.getGasLimit().compareTo(BigInteger.valueOf(214590)) < 0) {
            codeResp.setGasLimit(BigInteger.valueOf(214590));
        }
        return  chainService.createAndSendTransaction(
                codeResp, blockchain, contractAddress.toHexString(true), privateKey);
    }
}
