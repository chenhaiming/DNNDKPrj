package comqh.chm.dnndkprj;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by ASUS on 2018/11/6.
 */
public class CalculatorTest {

    Calculator calculator;
    @Before
    public void before(){
        System.out.print("before\n");
        calculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        System.out.print(calculator.sum(12,3)+"\n");
    }
    @After
    public void agter(){
        System.out.print("end\n");
    }
}