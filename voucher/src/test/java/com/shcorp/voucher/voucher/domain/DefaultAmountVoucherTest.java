package com.shcorp.voucher.voucher.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultAmountVoucherTest {
	@Test
	void testApplyDiscount() {
		// given
		DefaultAmountVoucher defaultAmountVoucher = new DefaultAmountVoucher("code");
		Money money = new Money(100);

		// when
		Money result = defaultAmountVoucher.applyDiscount(money);

		// then
		assertEquals("code", defaultAmountVoucher.getCode());
		assertEquals(money, result);
	}
}