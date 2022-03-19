package com.esiran.libtokenagentj.common;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

public class HashTest {
    @Test
    public void testToHexString(){
        byte[] data = new byte[]{
                -1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        String wantHex = "ff00010000000000000000000000000000000000000000000000000000000000";
        Hash hash = new Hash(data);
        // 字节数组转十六进制字符串
        String gotHex = hash.toHexString();
        assert gotHex.equals(wantHex.toLowerCase(Locale.ROOT));
    }

    @Test
    public void testCreateOfBytes(){
        // 创建并测试长数据
        byte[] longData = new byte[64];
        Arrays.fill(longData, (byte) -1);
        Hash got = Hash.of(longData);
        assert got.getData().length == Hash.DATA_LEN;
        byte[] wantBytes = Arrays.copyOf(longData, Hash.DATA_LEN);
        assert Arrays.equals(wantBytes, got.getData());
        // 创建并测试短数据
        byte[] shortData = new byte[]{-1,-1,0,1};
        wantBytes = new byte[]{
                -1,-1,0,1,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
        };
        got = Hash.of(shortData);
        assert got.getData().length == Hash.DATA_LEN;
        assert Arrays.equals(wantBytes, got.getData());
    }
    @Test
    public void testCreateOfHex(){
        byte[] data = new byte[]{
                -1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        String hexString = "ff00010000000000000000000000000000000000000000000000000000000000";
        Hash hash = Hash.ofHex(hexString);
        assert Arrays.equals(data, hash.getData());
        String hashHex = hash.toHexString();
        assert hexString.equals(hashHex.toLowerCase());
    }
}
