package com.esiran.libtokenagentj.util;

import com.esiran.libtokenagentj.common.Address;
import org.web3j.crypto.ContractUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

public class AddressUtil {
    public static Address getAddressFromPrivateKey(byte[] privateKey){
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        Credentials c = Credentials.create(keyPair);
        return Address.ofHex(c.getAddress());
    }

    public static Address createAddress(Address address, BigInteger nonce){
        return Address.ofHex(ContractUtils.generateContractAddress(
                address.toHexString(true), nonce));
    }
}
