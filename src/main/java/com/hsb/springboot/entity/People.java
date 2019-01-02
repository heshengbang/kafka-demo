package com.hsb.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/2 14:46
 * @author heshengbang
 */
@Data
public class People implements Serializable {
    private String name;
    private Date born;
    private int age;
}
