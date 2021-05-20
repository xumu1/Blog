```java
/**
 * @(#)Gender.java, 一月 13, 2020.
 * <p>
 * Copyright 2020 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fenbi.commons.core.util.MapUtils;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * @author yongcun
 */
public enum  Gender {

    @SerializedName("-1")
    UNKNOWN(-1, "未知"),

    @SerializedName("0")
    MALE(0, "男"),

    @SerializedName("1")
    FEMALE(1, "女");

    private static Map<Integer, Gender> GENDER_MAP = Maps.newHashMap();

    static {
        for (Gender gender : Gender.values()) {
            GENDER_MAP.put(gender.value, gender);
        }
    }

    private int value;
    private String name;

    Gender(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static Gender parse(int value) {
        return (Gender) MapUtils.getObject(GENDER_MAP, value, UNKNOWN);
    }
}
```