package owu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owu.Utils.NumberGenerator;
import owu.domains.NumberModel;
import owu.exceptions.DatabaseException;
import owu.repository.NumberRepository;
import owu.wire.GameResponse;

import java.util.List;

@Service
public class GameImpl {

    private NumberGenerator numberGenerator;
    private NumberRepository numberRepository;

    @Autowired
    public GameImpl(NumberGenerator numberGenerator, NumberRepository numberRepository) {
        this.numberGenerator = numberGenerator;
        this.numberRepository = numberRepository;
    }


    public NumberModel saveNumber(int guess) {
        NumberModel numberModel = new NumberModel();
        numberModel.setGuess(guess);
        numberRepository.save(numberModel);
        return numberModel;
    }

    public NumberModel saveRandomNumber() {
        NumberModel numberModel = new NumberModel();
        numberModel.setGuess(numberGenerator.generateNumber());
        numberRepository.save(numberModel);
        return numberModel;
    }

    public List<NumberModel> findAll() {
        List<NumberModel> all = numberRepository.findAll();
        return all;
    }

    public GameResponse check(Integer id, int guess) {
        NumberModel one = numberRepository.findOne(id);
        try {
            one.getId();
        } catch (NullPointerException e) {
            throw new DatabaseException("there is no record for id - " + id);
        }
        if (one.getGuess() == guess) {
            return new GameResponse("Winner", guess);
        } else return new GameResponse("Loooser", guess);
    }

    public NumberModel findById(Integer id) {
        NumberModel one = numberRepository.findOne(id);
        try {
            one.getId();
        } catch (NullPointerException e) {
            throw new DatabaseException("there is no record for id - " + id);
        }
        return one;
    }
}
