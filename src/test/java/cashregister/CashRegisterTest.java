package cashregister;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @BeforeEach
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        //given
        Purchase purchase =new Purchase(new Item[]{
                new Item("A", 10.0),
                new Item("B", 15.0),
                new Item("C", 20.0)
        });
        CashRegister cashRegister = new CashRegister(new Printer());
        //when
        cashRegister.process(purchase);
        //then
        assertThat(outContent.toString()).isEqualTo("A\t10.0\nB\t15.0\nC\t20.0\n");
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        //given
        Purchase purchase =new Purchase(new Item[]{
                new Item("A", 10.0),
                new Item("B", 15.0),
                new Item("C", 20.0)
        });
        CashRegister cashRegister = new CashRegister(new Printer(){
            @Override
            public void print(String printThis) {
                System.out.print("sup:"+printThis);
                //throw new UnsupportedOperationException("Not Implemented");
            }
        });
        //when
        cashRegister.process(purchase);
        //then
        assertThat(outContent.toString()).isEqualTo("sup:A\t10.0\nB\t15.0\nC\t20.0\n");
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        //given
        Purchase purchase =new Purchase(new Item[]{
                new Item("A", 10.0),
                new Item("B", 15.0),
                new Item("C", 20.0)
        });
        Printer printer = mock(Printer.class);
        CashRegister cashRegister = new CashRegister(printer);
        //when
        cashRegister.process(purchase);
        //then
       // assertThat(outContent.toString()).isEqualTo("A\t10.0\nB\t15.0\nC\t20.0\n");
        verify(printer).print("A\t10.0\nB\t15.0\nC\t20.0\n");
    }

}
