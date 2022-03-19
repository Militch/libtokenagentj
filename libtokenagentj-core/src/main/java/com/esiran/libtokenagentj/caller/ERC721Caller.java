package com.esiran.libtokenagentj.caller;

import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.service.params.NFTMintParam;

public interface ERC721Caller {
    String getName() throws Exception;
    String getSymbol() throws Exception;
    Hash mintToken(NFTMintParam nftMintParam, byte[] privateKey) throws Exception;
}
