package com.oliver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Users implements Serializable {



    @TableId(value = "UserID", type = IdType.AUTO)
    private Integer UserID;

    private String Username;

    private String Password;

    private String Phone;

    private String WeChatID;


}
