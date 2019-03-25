package owu.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import owu.domains.NumberModel;
import owu.repository.NumberRepository;
import owu.service.GameImpl;
import owu.wire.GameResponse;
import owu.wire.Message;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class MainController {

    private GameImpl gameImpl;
    private NumberRepository numberRepository;

    @Autowired
    public MainController(GameImpl gameImpl, NumberRepository numberRepository) {
        this.gameImpl = gameImpl;
        this.numberRepository = numberRepository;
    }


    @PostMapping("/random")
    @ResponseBody
    public ResponseEntity<NumberModel> generateRandomNumber() {
        return ResponseEntity.ok().body(gameImpl.saveRandomNumber());
    }

    @GetMapping("/play/{id}/{guess}")
    @ResponseBody
    public ResponseEntity<GameResponse> playGame(
            @PathVariable int guess,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok().body(gameImpl.check(id, guess));
    }

    @GetMapping("/byId/{id}")
    @ResponseBody
    public ResponseEntity<NumberModel> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok().body(gameImpl.findById(id));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<NumberModel>> get() {
        return ResponseEntity.ok().body(gameImpl.findAll());
    }


    @PostMapping("/create/{number}")
    @ResponseBody
    public ResponseEntity<NumberModel> setNumber(
            @PathVariable @Min(0) @Max(value = 12, message = "create number in range 0 - 12 only!") Integer number
    ) {
        return ResponseEntity.ok().body(gameImpl.saveNumber(number));
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity hello() {
        return ResponseEntity.ok().body(new Message());
    }



}
