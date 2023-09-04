package com.oliver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author oliver
 * @since 2023-09-04
 */
@Getter
@Setter
@Data
public class Counselingservices implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ServiceID", type = IdType.AUTO)
    private Integer ServiceID;

    private String Type;

    private BigDecimal Fee;

    private Integer Duration;

    private Integer CounselorID;
}
