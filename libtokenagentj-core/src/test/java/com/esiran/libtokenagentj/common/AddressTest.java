package com.esiran.libtokenagentj.common;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

public class AddressTest {
    @Test
    public void testToHexString(){
        byte[] data = new byte[]{
                -1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };
        String wantHex = "ff00010000000000000000000000000000000000";
        Address address = new Address(data);
        // 字节数组转十六进制字符串
        String gotHex = address.toHexString();
        assert gotHex.equals(wantHex.toLowerCase(Locale.ROOT));
    }

    @Test
    public void testCreateOfBytes(){
        // 创建并测试长数据
        byte[] longData = new byte[64];
        Arrays.fill(longData, (byte) -1);
        Address got = Address.of(longData);
        assert got.getData().length == Address.DATA_LEN;
        byte[] wantBytes = Arrays.copyOf(longData, Address.DATA_LEN);
        assert Arrays.equals(wantBytes, got.getData());
        // 创建并测试短数据
        byte[] shortData = new byte[]{-1, 0, 1};
        wantBytes = new byte[]{
                -1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };
        got = Address.of(shortData);
        assert got.getData().length == Address.DATA_LEN;
        assert Arrays.equals(wantBytes, got.getData());
    }
    @Test
    public void testCreateOfHex(){
        byte[] data = new byte[]{
                -1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };
        String hexString = "ff00010000000000000000000000000000000000";
        Address address = Address.ofHex(hexString);
        assert Arrays.equals(data, address.getData());
        String hashHex = address.toHexString();
        assert hexString.equals(hashHex.toLowerCase());
    }
}
