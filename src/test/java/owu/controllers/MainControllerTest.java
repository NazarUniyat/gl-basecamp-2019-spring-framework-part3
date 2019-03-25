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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import owu.config.WebConfig;
import owu.domains.NumberModel;
import owu.repository.NumberRepository;
import owu.service.GameImpl;


import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration("owu.config")
public class MainControllerTest {

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
    public void givenURI_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(get("/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void givenURIRandom_whenMockMVC_thenVerifyResponseThatContainNumberModelObject() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(post("/random"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void givenURIWithNumberToCreate_whenMockMVC_thenVerifyResponseThatContainCreatedObject() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(post("/create/7"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.guess").value("7"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void givenURIPlayNumberIdGuessWithPathVariable_whenMockMVC_thenVerifyResponseThatContainWinningResult() throws Exception {
        numberRepository.save(new NumberModel(1,145));
        MvcResult mvcResult = this.mockMvc
                .perform(get("/play/1/145"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Winner"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void givenURIPlayNumberIdGuessWithPathVariable_whenMockMVC_thenVerifyResponseThatContainLosingResult() throws Exception {
        numberRepository.save(new NumberModel(1,1));
        MvcResult mvcResult = this.mockMvc
                .perform(get("/play/1/145"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Loooser"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }




}
