package com.ljw.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义ljw异常
 *
 * @author Luo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LjwException extends RuntimeException {

    private Integer code;

    private String msg;
}
