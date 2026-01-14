package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.0001);
		assertEquals(0.20, DKK.getRate(), 0.0001);
		assertEquals(1.5, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.25);
		assertEquals(0.25, SEK.getRate(), 0.0001);
		SEK.setRate(0.15);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK.universalValue(10000));
		assertEquals(Integer.valueOf(3000), DKK.universalValue(15000));
		assertEquals(Integer.valueOf(0), EUR.universalValue(0));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(10000), SEK.valueInThisCurrency(10000, SEK));
		assertEquals(Integer.valueOf(1000), EUR.valueInThisCurrency(10000, SEK));
		assertEquals(Integer.valueOf(13333), SEK.valueInThisCurrency(10000, DKK));
	}

}
