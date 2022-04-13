package com.esiran.libtokenagentj.service;

import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.params.*;

public class ERC20TokenService {
    private final RPCClient client;

    public ERC20TokenService(RPCClient client) {
        this.client = client;
    }
    public String getName(ContractCallRequest request) throws Exception {
        return client.call("Token.Name", request, String.class);
    }
    public String getSymbol(ContractCallRequest request) throws Exception {
        return client.call("Token.Symbol", request, String.class);
    }
    public String getTotalSupply(ContractCallRequest request) throws Exception {
        return client.call("Token.TotalSupply", request, String.class);
    }
    public String getBalanceOf(TokenBalanceOfRequest request) throws Exception {
        return client.call("Token.BalanceOf", request, String.class);
    }
    public CodeResp requestPreCreate(ERC20TokenCreateRequest request) throws Exception {
        return client.call("Token.PreCreate", request, CodeResp.class);
    }
    public CodeResp requestPreMint(ERC20TokenPreMintRequest request) throws Exception {
        return client.call("Token.PreMint", request, CodeResp.class);
    }
    public CodeResp requestPreTransferFrom(ERC20TokenTransferRequest request) throws Exception {
        return client.call("Token.PreTransferFrom", request, CodeResp.class);
    }
}
