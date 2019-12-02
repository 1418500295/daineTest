package com.toutiao.model.toutiaomd;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareCase {
    private String id;
    private String type;
    private int number_id;
    private String user_id;
    private int expResult;
}
