package com.esiran.libtokenagentj;

import com.esiran.libtokenagentj.core.CollectionCreateParams;
import com.esiran.libtokenagentj.core.CollectionCreateResult;
import com.esiran.libtokenagentj.common.Address;
import com.esiran.libtokenagentj.common.Hash;
import com.esiran.libtokenagentj.core.TokenAgentClient;
import com.esiran.libtokenagentj.mock.MockRPCClient;
import com.esiran.libtokenagentj.service.params.NFTMintRequestParams;
import com.esiran.libtokenagentj.util.AddressUtil;
import com.esiran.libtokenagentj.util.BytesUtil;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.util.Arrays;

public class TokenAgentClientTest {

    private static class TestKeyItem {
        private final Address address;
        private final byte[] privateKey;
        private TestKeyItem(Address address, byte[] privateKey) {
            this.address = address;
            this.privateKey = privateKey;
        }

        public Address getAddress() {
            return address;
        }

        public byte[] getPrivateKey() {
            return privateKey;
        }
    }
    private static TestKeyItem createTestKeyItemOfHex(String hex){
        byte[] privateKey = BytesUtil.hex2bytes(hex);
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        Credentials c = Credentials.create(keyPair);
        Address address = Address.ofHex(c.getAddress());
        return new TestKeyItem(address, privateKey);
    }
    private final MockRPCClient rpcClient = new MockRPCClient();
    private final TokenAgentClient client = new TokenAgentClient(rpcClient, null);
    private final TestKeyItem[] keyItems = new TestKeyItem[]{
            createTestKeyItemOfHex("d2850e08eb14cec5fcdf3f7e1e8d502a8cab3de03de1d15dcfc35317dbe3d709"),
    };
    @Test
    public void testCreateCollection() throws Exception {
        String mockJson = "[ "+
                "{\"request\":{" +
                "    \"jsonrpc\":\"2.0\"," +
                "    \"method\":\"Collection.PreCreate\"," +
                "    \"id\":1," +
                "    \"params\":{" +
                "        \"blockchain\":\"ETH_\"," +
                "        \"from\": \"0x01111111111111\"," +
                "        \"name\":\"demo\"," +
                "        \"symbol\":\"demo\"," +
                "        \"signeraddress\":\"0xD5d96202f7e7a86c752bc10Fb70e4fC8f3E89fDB\"," +
                "        \"contracturi\":\"https://ipfs.io/ipfs/\"," +
                "        \"tokenuriprefix\":\"https://ipfs.io/ipfs/\"" +
                "    } }," +
                "\"response\":{" +
                "    \"id\":1," +
                "    \"jsonrpc\":\"2.0\"," +
                "    \"result\":{" +
                "        \"gasprice\":\"1000000000\"," +
                "        \"gaslimit\":\"218808\"," +
                "        \"nonce\":\"8\"," +
                "        \"code\":\"0x608060405234801561001057600080fd5b506040516103b33803806103b383398101604081905261002f916100f8565b8051610042906000906020840190610049565b5050610202565b828054610055906101c7565b90600052602060002090601f01602090048101928261007757600085556100bd565b82601f1061009057805160ff19168380011785556100bd565b828001600101855582156100bd579182015b828111156100bd5782518255916020019190600101906100a2565b506100c99291506100cd565b5090565b5b808211156100c957600081556001016100ce565b634e487b7160e01b600052604160045260246000fd5b6000602080838503121561010b57600080fd5b82516001600160401b038082111561012257600080fd5b818501915085601f83011261013657600080fd5b815181811115610148576101486100e2565b604051601f8201601f19908116603f01168101908382118183101715610170576101706100e2565b81604052828152888684870101111561018857600080fd5b600093505b828410156101aa578484018601518185018701529285019261018d565b828411156101bb5760008684830101525b98975050505050505050565b600181811c908216806101db57607f821691505b602082108114156101fc57634e487b7160e01b600052602260045260246000fd5b50919050565b6101a2806102116000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80631f1bd69214610030575b600080fd5b61003861004e565b60405161004591906100dc565b60405180910390f35b6000805461005b90610131565b80601f016020809104026020016040519081016040528092919081815260200182805461008790610131565b80156100d45780601f106100a9576101008083540402835291602001916100d4565b820191906000526020600020905b8154815290600101906020018083116100b757829003601f168201915b505050505081565b600060208083528351808285015260005b81811015610109578581018301518582016040015282016100ed565b8181111561011b576000604083870101525b50601f01601f1916929092016040019392505050565b600181811c9082168061014557607f821691505b6020821081141561016657634e487b7160e01b600052602260045260246000fd5b5091905056fea2646970667358221220944d6cfc92a3ad17ed4ec2982a02ad0d45b6efc1c8bb4efd8763941769f067ff64736f6c634300080c0033000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000036162630000000000000000000000000000000000000000000000000000000000\"" +
                "    }"+
                "} }," +
                "{\"request\":{" +
                "    \"jsonrpc\":\"2.0\"," +
                "    \"method\":\"Chain.SendRawTransaction\"," +
                "    \"id\":1," +
                "    \"params\":{" +
                "        \"data\":\"0xff\"" +
                "    }" +
                " }," +
                "\"response\":{" +
                "    \"id\":1," +
                "    \"jsonrpc\":\"2.0\"," +
                "    \"result\":{" +
                "        \"hash\":\"0xd915069a51da3b40d9b0c3be8403c9db6c7809a2203b7835a8cf66c11eb7a842\"" +
                "    }"+
                "} }" +
                "]";
        rpcClient.setJsonMock(mockJson);
        TestKeyItem keyItem = keyItems[0];
        CollectionCreateParams createParams = new CollectionCreateParams();
        createParams.setName("abc");
        CollectionCreateResult resp = client.createCollection(createParams, keyItem.getPrivateKey());
        Address address = resp.getContractAddress();
        assert address != null;
        Address cAddress = AddressUtil.createAddress(keyItem.address, BigInteger.valueOf(8));
        assert Arrays.equals(address.getData(), cAddress.getData());
        Hash txHash = resp.getTransactionHash();
        assert txHash != null;
    }

    @Test
    public void testMiteNft() throws Exception {
    }

    @Test
    public void uploadFile() throws Exception {
        // 准备私钥
        byte[] key = new byte[]{};
        // ...
        // 创建合集
        NFTMintRequestParams nftMiteParams = new NFTMintRequestParams();
    }
}
