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
public class Counselors implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "CounselorID", type = IdType.AUTO)
    private Integer CounselorID;

    private String Name;

    private String Gender;

    private String Title;

    private String Introduction;

    private String Qualifications;

    private Integer UserID;

    private String ImgURL;
}
