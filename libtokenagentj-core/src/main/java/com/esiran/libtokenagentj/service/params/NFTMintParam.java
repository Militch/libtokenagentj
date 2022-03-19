package com.esiran.libtokenagentj.service.params;

import lombok.Data;

import java.math.BigInteger;

@Data
public class NFTMintParam {
    /**
     * 藏品ID
     */
    private BigInteger tokenId;
    /**
     * 藏品url
     */
    private String tokenUri;
}
