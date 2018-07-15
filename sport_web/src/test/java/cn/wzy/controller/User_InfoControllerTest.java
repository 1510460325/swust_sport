package cn.wzy.controller;

import cn.wzy.util.BaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Create by Wzy
 * on 2018/7/14 13:39
 * 不短不长八字刚好
 */
public class User_InfoControllerTest extends BaseControllerTest {

    @Test
    public void register() throws Exception {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/user/register.do")
                        .param("usName","5120162154")
                        .param("usPassword","202cb962ac59075b964b07152d234b70")
                        .param("usNickname","1123")
                        .header("Authorization","eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ3YW5nIiwicm9sZUlkIjoyLCJpc3MiOiJ3YW5nIiwiZXhwIjoxNTI2MTI0NjA1LCJ1c2VySWQiOjEsImlhdCI6MTUyNjAzODIwNX0.RC1Mbv_NljU1XFb5VtWtHThgVboqNv-6dcCQkO3AYJg")
                );
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
