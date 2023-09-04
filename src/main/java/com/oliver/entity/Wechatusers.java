package com.oliver.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author oliver
 * @since 2023-08-29
 */
@Getter
@Setter
@Data
public class Wechatusers implements Serializable {



    private String WeChatID;

    private Integer UserID;


}
