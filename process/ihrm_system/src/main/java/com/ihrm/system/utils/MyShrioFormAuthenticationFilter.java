package com.ihrm.system.utils;

import com.alibaba.fastjson.JSON;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义shrio认证失败的处理，不采用默认的跳转
 *dl
 * @author caorui
 * @date 2020/3/9
 */
public class MyShrioFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response,
                                     Object mappedValue) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Result result = new Result(ResultCode.UNAUTHENTICATED);
        out.write(JSON.toJSONString(result)); // 返回自己的json
        out.flush();
        out.close();
        return false;
    }
}
