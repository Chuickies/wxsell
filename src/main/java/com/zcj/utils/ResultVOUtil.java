package com.zcj.utils;

import com.zcj.vo.ResultVo;

public class ResultVOUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMessage(msg);
        return resultVO;
    }
}
