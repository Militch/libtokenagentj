package com.esiran.libtokenagentj.encoder;

import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易编码器
 */
public class ETHTransactionEncoder {
    private final ETHSigner signer;
    public ETHTransactionEncoder(ETHSigner signer) {
        this.signer = signer;
    }

    public byte[] encode(ETHTransaction tx, Sign.SignatureData signed){
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(tx.getNonce()));

        result.add(RlpString.create(tx.getGasPrice()));
        result.add(RlpString.create(tx.getGasLimit()));

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = tx.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(tx.getValue()));

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(tx.getData());
        result.add(RlpString.create(data));
        if (signed != null) {
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signed.getV())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signed.getR())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signed.getS())));
        }
        RlpList rlpList = new RlpList(result);
        return RlpEncoder.encode(rlpList);
    }

    public byte[] encodeAndSign(ETHTransaction tx){
        byte[] payload = encode(tx, null);
        return encode(tx, signer.sign(payload));
    }
    public byte[] createHash(ETHTransaction tx){
        byte[] payload = encode(tx, null);
        return Hash.sha3(payload);
    }
}
