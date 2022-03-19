package com.esiran.libtokenagentj.service;

import com.esiran.libtokenagentj.common.CodeResp;
import com.esiran.libtokenagentj.service.params.ContractCallRequest;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import com.esiran.libtokenagentj.service.params.CollectionCreateRequest;

public class CollectionService {
    private final RPCClient client;
    public CollectionService(RPCClient client) {
        this.client = client;
    }

    public String getCollectionName(ContractCallRequest request) throws Exception {
        return client.call("Collection.Name", request, String.class);
    }

    public String getCollectionSymbol(ContractCallRequest request) throws Exception {
        return client.call("Collection.Symbol", request, String.class);
    }

    public CodeResp requestCollectionPreCreate(CollectionCreateRequest params) throws Exception {
        return client.call("Collection.PreCreate", params, CodeResp.class);
    }
}
