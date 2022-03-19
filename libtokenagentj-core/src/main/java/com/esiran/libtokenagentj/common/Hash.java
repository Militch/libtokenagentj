package com.esiran.libtokenagentj.common;


import com.esiran.libtokenagentj.util.BytesUtil;

import java.util.Arrays;

public class Hash {
    public static final int DATA_LEN = 32;
    private final byte[] data;
    public Hash(byte[] data) {
        if (data.length != DATA_LEN){
            throw new IllegalArgumentException(String.format(
                    "hash data.length must be %s bits", DATA_LEN * 8));
        }
        this.data = data;
    }
    public static Hash of(byte[] data){
        if (data == null){
            return null;
        }
        byte[] nData = Arrays.copyOfRange(data, 0, DATA_LEN);
        return new Hash(nData);
    }

    public byte[] getData() {
        return data;
    }

    public static Hash ofHex(String hex){
        byte[] bytes = BytesUtil.hex2bytes(hex);
        return of(bytes);
    }
    public String toHexString(){
        return toHexString(false);
    }
    public String toHexString(boolean prefix){
        return BytesUtil.bytes2hex(data, prefix);
    }
}
