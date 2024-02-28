package fr.em_ilien.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeProviderTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCurrentYearValue() {
		assertThat(TimeProvider.currentYearValue()).isEqualTo(Calendar.getInstance().get(Calendar.YEAR));
	}
	
	@Test
	void testConstructClass() {
		new TimeProvider();
	}

}
