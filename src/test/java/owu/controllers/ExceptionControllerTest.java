package owu.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import owu.config.WebConfig;
import owu.repository.NumberRepository;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration("owu.config.")
public class ExceptionControllerTest {

    private WebApplicationContext wac;
    private NumberRepository numberRepository;

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Autowired
    public void setNumberRepository(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void isStartup(){
        ServletContext servletContext = wac.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("entityManagerFactory"));
    }

    @Test
    public void givenByIdURIWithPathVariable_whenMockMVC_thenReturnResponseWithException() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(get("/byId/0"))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DB querying exception occurred:  there is no record for id - 0"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

}