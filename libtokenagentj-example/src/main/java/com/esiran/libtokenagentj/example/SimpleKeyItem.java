package com.esiran.libtokenagentj.example;

import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.util.BytesUtil;
import lombok.Data;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

@Data
public class SimpleKeyItem {
    private final Address address;
    private final byte[] privateKey;
    private SimpleKeyItem(Address address, byte[] privateKey) {
        this.address = address;
        this.privateKey = privateKey;
    }
    public static SimpleKeyItem createSimpleKeyItemOfHex(String hex){
        byte[] privateKey = BytesUtil.hex2bytes(hex);
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        Credentials c = Credentials.create(keyPair);
        Address address = Address.ofHex(c.getAddress());
        return new SimpleKeyItem(address, privateKey);
    }

    public static SimpleKeyItem createRandomKeyItem() {
        byte[] privateKey = BytesUtil.random(32);
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        Credentials c = Credentials.create(keyPair);
        Address address = Address.ofHex(c.getAddress());
        return new SimpleKeyItem(address, privateKey);
    }
}
