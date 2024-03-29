package com.shcorp.voucher.voucher.application.port.in;

import com.shcorp.voucher.voucher.domain.SelfValidating;
import com.shcorp.voucher.voucher.domain.VoucherType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreatVoucherCommand extends SelfValidating<CreatVoucherCommand> {
	@NotNull
	private final VoucherType voucherType;
	@Min(0)
	private final int amount;

	public CreatVoucherCommand(VoucherType voucherType, int amount) {
		this.voucherType = voucherType;
		this.amount = amount;
		super.validateSelf();
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public int getAmount() {
		return amount;
	}
}
