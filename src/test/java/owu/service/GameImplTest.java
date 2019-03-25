package owu.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import owu.Utils.NumberGenerator;
import owu.config.WebConfig;
import owu.domains.NumberModel;
import owu.repository.NumberRepository;
import owu.wire.GameResponse;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration("owu.config")
public class GameImplTest {


    private WebApplicationContext wac;
    private NumberRepository numberRepository;
    private NumberGenerator numberGenerator;
    private GameImpl game;

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Autowired
    public void setNumberRepository(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Autowired
    public void setNumberGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    @Autowired
    public void setGame(GameImpl game) {
        this.game = game;
    }

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void isStartup() {
        ServletContext servletContext = wac.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(wac.getBean("entityManagerFactory"));
    }

    @Test
    public void givenNumberToSave_whenSaveNumber_thenReturnSavedNumberModelClass() {
        NumberModel numberModel = game.saveNumber(12);
        int id = numberModel.getId();
        NumberModel one = numberRepository.findOne(id);
        assertEquals(12, one.getGuess());
    }


    @Test
    public void givenParamsToPlayGame_whenCheck_thenCheckAndReturnGameResult() {
        numberRepository.save(new NumberModel(1, 1234));
        System.out.println(numberRepository.findOne(1));
        assertEquals(new GameResponse("Winner", 1234), game.check(1, 1234));
    }

    @Test
    public void givenIdOfNumberModel_whenFindById_thenReturnNumberModelThatWasFound() {
        NumberModel numberModel = new NumberModel(1, 178);
        numberRepository.save(numberModel);
        assertEquals(game.findById(1), numberModel);
    }
}