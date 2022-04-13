package com.esiran.libtokenagentj.core;

import com.esiran.libtokenagentj.caller.ERC20TokenCallerParams;
import com.esiran.libtokenagentj.caller.TokenCallerParams;
import com.esiran.libtokenagentj.caller.CollectionCallerParams;

import java.io.File;

public interface ERC721AgentClient {
    CollectionCreateResult createCollection(CollectionCreateParams params, byte[] privateKey) throws Exception;
    CollectionCaller newCollection(CollectionCallerParams callerParams) throws Exception;
    TokenCaller newToken(TokenCallerParams params) throws Exception;
    ERC20TokenCreateResult createERC20Token(ERC20TokenCreateParams params, byte[] privateKey) throws Exception;
    ERC20TokenCaller newERC20Token(ERC20TokenCallerParams params);
    String uploadFileToIPFS(File file) throws Exception;
}
