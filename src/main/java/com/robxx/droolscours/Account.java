package com.robxx.droolscours;

public class Account {

	private long accountno;
	private double balance;

	public long getAccountno() {
		return accountno;
	}

	public void setAccountno(long accountno) {
		this.accountno = accountno;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "Account [accountno=" + accountno + ", balance=" + balance + "]";
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
