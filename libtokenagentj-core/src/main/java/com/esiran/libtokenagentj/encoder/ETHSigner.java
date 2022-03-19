package com.esiran.libtokenagentj.encoder;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

public class ETHSigner {
    private final byte[] key;
    public ETHSigner(byte[] key) {
        this.key = key;
    }

    public Sign.SignatureData sign(byte[] data){
        ECKeyPair keyPair = ECKeyPair.create(key);
        return Sign.signMessage(data, keyPair);
    }
}
