package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("TestAccount");
		try {
			SweBank.openAccount("Ulrika");
			fail("Should throw AccountExistsException");
		} catch (AccountExistsException e) {
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
		SweBank.deposit("Ulrika", new Money(3000, SEK));
		assertEquals(Integer.valueOf(8000), SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.withdraw("Ulrika", new Money(3000, SEK));
		assertEquals(Integer.valueOf(7000), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(15000, SEK));
		assertEquals(Integer.valueOf(15000), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.deposit("Bob", new Money(5000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(3000, SEK));
		assertEquals(Integer.valueOf(7000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(8000), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.addTimedPayment("Ulrika", "payment1", 3, 1, new Money(1000, SEK), SweBank, "Bob");
		SweBank.tick();
		assertEquals(Integer.valueOf(10000), SweBank.getBalance("Ulrika"));
		SweBank.tick();
		assertEquals(Integer.valueOf(9000), SweBank.getBalance("Ulrika"));
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		assertEquals(Integer.valueOf(8000), SweBank.getBalance("Ulrika"));
	}
}
