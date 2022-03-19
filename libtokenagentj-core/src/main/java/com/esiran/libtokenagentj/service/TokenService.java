package com.esiran.libtokenagentj.service;

import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.params.NFTMintRequestParams;
import com.esiran.libtokenagentj.service.params.TokenApproveRequest;
import com.esiran.libtokenagentj.service.params.TokenCallerRequest;
import com.esiran.libtokenagentj.service.params.TokenTransferRequest;

public class TokenService {
    private final RPCClient client;

    public TokenService(RPCClient client) {
        this.client = client;
    }

    public CodeResp requestPreMint(NFTMintRequestParams params) throws Exception {
        return client.call("NFT.PreMint", params, CodeResp.class);
    }

    public String getTokenUri(TokenCallerRequest request) throws Exception {
        return client.call("NFT.TokenUri", request, String.class);
    }

    public CodeResp requestPreTransferFrom(TokenTransferRequest request) throws Exception {
        return client.call("NFT.PreTransferFrom", request, CodeResp.class);
    }


    public CodeResp requestPreApprove(TokenApproveRequest request) throws Exception {
        return client.call("NFT.PreApprove", request, CodeResp.class);
    }

}
