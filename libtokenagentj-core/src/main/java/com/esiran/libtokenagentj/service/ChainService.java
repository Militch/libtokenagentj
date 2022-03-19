package com.esiran.libtokenagentj.service;

import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.common.TXHashResp;
import com.esiran.libtokenagentj.encoder.ETHSigner;
import com.esiran.libtokenagentj.encoder.ETHTransaction;
import com.esiran.libtokenagentj.encoder.ETHTransactionEncoder;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ChainService {
    private final RPCClient client;

    public ChainService(RPCClient client) {
        this.client = client;
    }

    public Hash requestSendRawTransaction(String blockChain, byte[] data) throws Exception {
        String hex = Numeric.toHexString(data);
        Map<String,Object> requestData = new HashMap<>();
        requestData.put("data", hex);
        requestData.put("blockchain", blockChain);
        TXHashResp txHashResp = client.call("Chain.SendRawTransaction", requestData, TXHashResp.class);
        return Hash.ofHex(txHashResp.getHash());
    }

    public Hash createAndSendTransaction(CodeResp codeResp, String blockChain, String to, byte[] privateKey) throws Exception {
        // 构造交易
        ETHTransaction tx = new ETHTransaction();
        tx.setValue(BigInteger.ZERO);
        tx.setGasPrice(codeResp.getGasPrice());
        tx.setGasLimit(codeResp.getGasLimit());
        tx.setNonce(codeResp.getNonce());
        tx.setData(codeResp.getCode());
        tx.setTo(to);
        // 构造交易编码器
        ETHSigner signer = new ETHSigner(privateKey);
        ETHTransactionEncoder encoder = new ETHTransactionEncoder(signer);
        // 编码并且签名
        byte[] signedMessage = encoder.encodeAndSign(tx);
        // 发送交易上链
        return requestSendRawTransaction(blockChain, signedMessage);
    }
}
