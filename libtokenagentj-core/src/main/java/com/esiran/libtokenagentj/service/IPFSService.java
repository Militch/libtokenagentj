package com.esiran.libtokenagentj.service;

import com.esiran.libtokenagentj.jsonrpc.IPFSResult;
import com.esiran.libtokenagentj.jsonrpc.RPCClient;
import okhttp3.MediaType;

import java.io.File;

public class IPFSService {
    private final RPCClient client;

    public IPFSService(RPCClient client) {
        this.client = client;
    }

    public String upload(File file, String  mediaType) throws Exception {
        IPFSResult result = client.upload(file, mediaType, IPFSResult.class);
        if (result == null){
            return null;
        }
        return result.getIpfs();
    }
}
