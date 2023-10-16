package com.oliver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
@Getter
@Setter
@Data
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String summary;

    private String imageUrl;

    private String linkUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
