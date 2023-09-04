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
 * @since 2023-09-04
 */
@Getter
@Setter
@Data
public class Userreviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ReviewID", type = IdType.AUTO)
    private Integer ReviewID;

    private Integer UserID;

    private Integer CounselorID;

    private Integer Rating;

    private String Feedback;
}
