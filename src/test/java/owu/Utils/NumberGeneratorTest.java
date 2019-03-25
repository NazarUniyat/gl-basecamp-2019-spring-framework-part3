package owu.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberGeneratorTest {

    @Test
    public void givenRangeMinAndMaxNumbersToGenerateNumber_whenGenerateNumber_thenReturnGeneratedNumberInRange() {
        NumberGenerator numberGenerator = new NumberGenerator();
        numberGenerator.setMin(56);
        numberGenerator.setMax(58);
        int i = numberGenerator.generateNumber();
        assertEquals(true, i >= 56 && i <= 58);
    }
}