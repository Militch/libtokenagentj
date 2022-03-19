package com.esiran.libtokenagentj.encoder;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ETHTransaction {
    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;

}
