package fr.em_ilien.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

}
