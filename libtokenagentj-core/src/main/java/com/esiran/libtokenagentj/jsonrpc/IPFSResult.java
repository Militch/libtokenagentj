package com.esiran.libtokenagentj.jsonrpc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class IPFSResult {
    @SerializedName("ipfs")
    String ipfs;
}
