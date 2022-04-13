package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.caller.ERC20Caller;
import com.esiran.libtokenagentj.caller.ERC20TokenCallerParams;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.ChainService;
import com.esiran.libtokenagentj.service.ERC20TokenService;
import com.esiran.libtokenagentj.service.params.ContractCallRequest;
import com.esiran.libtokenagentj.service.params.ERC20TokenPreMintRequest;
import com.esiran.libtokenagentj.service.params.ERC20TokenTransferRequest;
import com.esiran.libtokenagentj.service.params.TokenBalanceOfRequest;
import com.esiran.libtokenagentj.util.AddressUtil;

import java.math.BigInteger;

public class ERC20TokenCaller implements ERC20Caller {
    private final ERC20TokenService tokenService;
    private final ChainService chainService;
    private final String blockchain;
    private final ERC20TokenCallerParams callerParams;
    public ERC20TokenCaller(RPCClient rpcClient, String blockchain,
                            ERC20TokenCallerParams callerParams) {
        tokenService = new ERC20TokenService(rpcClient);
        chainService = new ChainService(rpcClient);
        this.blockchain = blockchain;
        this.callerParams = callerParams;
    }

    @Override
    public String getName() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        return tokenService.getName(request);
    }

    @Override
    public String getSymbol() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        return tokenService.getSymbol(request);
    }

    @Override
    public BigInteger getTotalSupply() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        String value = tokenService.getTotalSupply(request);
        return new BigInteger(value);
    }

    @Override
    public Hash mint(ERC20TokenMintParam mintParam, byte[] privateKey) throws Exception {
        // 构造请求参数
        ERC20TokenPreMintRequest mintRequest = new ERC20TokenPreMintRequest();
        mintRequest.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        mintRequest.setContractAddress(contractAddress.toHexString(true));
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        mintRequest.setFromAddress(fromAddress.toHexString(true));
        Address toAddress = mintParam.getToAddress();
        mintRequest.setToAddress(toAddress.toHexString(true));
        BigInteger amount = mintParam.getAmount();
        mintRequest.setAmount(amount.toString(10));
        // 请求获取交易字节码
        CodeResp codeResp = tokenService.requestPreMint(mintRequest);
        // 请求发送交易
        return chainService.createAndSendTransaction(codeResp, blockchain,
                contractAddress.toHexString(true), privateKey);
    }

    @Override
    public BigInteger getBalance(Address address) throws Exception {
        TokenBalanceOfRequest request = new TokenBalanceOfRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        request.setAccount(address.toHexString(true));
        String balance = tokenService.getBalanceOf(request);
        return new BigInteger(balance);
    }

    @Override
    public Hash transferFrom(Address toAddress, BigInteger amount, byte[] privateKey) throws Exception {
        // 构造请求参数
        ERC20TokenTransferRequest request = new ERC20TokenTransferRequest();
        request.setBlockchain(blockchain);
        Address contractAddress = callerParams.getContractAddress();
        request.setContractAddress(contractAddress.toHexString(true));
        Address fromAddress = AddressUtil.getAddressFromPrivateKey(privateKey);
        request.setFromAddress(fromAddress.toHexString(true));
        request.setToAddress(toAddress.toHexString(true));
        request.setAmount(amount.toString(10));
        // 请求获取交易字节码
        CodeResp codeResp = tokenService.requestPreTransferFrom(request);
        // 发送交易
        return chainService.createAndSendTransaction(codeResp, blockchain,
                contractAddress.toHexString(true), privateKey);
    }

}
