package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.caller.ERC20Caller;
import com.esiran.libtokenagentj.caller.ERC20TokenCallerParams;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.ERC20TokenService;
import com.esiran.libtokenagentj.service.params.ContractCallRequest;

public class ERC20TokenCaller implements ERC20Caller {
    private final ERC20TokenService tokenService;
    private final String blockchain;
    private final ERC20TokenCallerParams callerParams;
    public ERC20TokenCaller(RPCClient rpcClient, String blockchain,
                            ERC20TokenCallerParams callerParams) {
        tokenService = new ERC20TokenService(rpcClient);
        this.blockchain = blockchain;
        this.callerParams = callerParams;
    }

    @Override
    public String getName() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        request.setContractAddress(callerParams.getContractAddress());
        return tokenService.getName(request);
    }

    @Override
    public String getSymbol() throws Exception {
        ContractCallRequest request = new ContractCallRequest();
        request.setBlockchain(blockchain);
        request.setContractAddress(callerParams.getContractAddress());
        return tokenService.getSymbol(request);
    }

    @Override
    public Hash mintCreate() {
        return null;
    }
}
