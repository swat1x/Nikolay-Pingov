package org.nikolay.pingov.json;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JsonPingDebug {

    boolean ping;
    boolean query;
    boolean srv;
    @SerializedName("ipinsrv")
    boolean inInSrv;

}
