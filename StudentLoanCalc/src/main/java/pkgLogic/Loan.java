package pkgLogic;

import java.time.LocalDate;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private int LoanPaymentCnt;
	private boolean bCompoundingOption;
	private LocalDate StartDate;
	private double AdditionalPayment;
	private double Escrow;
	private double Time;

	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();

	public Loan(double loanAmount, double interestRate, int loanPaymentCnt, LocalDate startDate,
			double additionalPayment, double escrow, double time) {
		super();
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		LoanPaymentCnt = loanPaymentCnt * 12;
		StartDate = startDate;
		AdditionalPayment = additionalPayment;
		bCompoundingOption = false;
		LoanBalanceEnd = 0;
		this.Escrow = escrow;
		Time = time;

		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		while (RemainingBalance >= this.GetPMT()) {
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
			RemainingBalance = payment.getEndingBalance();
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
		}

		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, true);
		loanPayments.add(payment);
	}

	public double GetPMT() {
		double PMT = 0;
		// PMT: periodic payment for the loan. See formula below.
		/// PMT = P * ( (i*(1+i)^n) / ((1+i)^n - 1) )
		double numerator1 = InterestRate*Math.pow(1+InterestRate,LoanPaymentCnt);
		double denominator1 = Math.pow(1+InterestRate,LoanPaymentCnt)-1;
		PMT = LoanAmount * (numerator1/denominator1)
		return PMT;
	}

	public double getTotalPayments() {
		// Returns the total payments for the loan. See formula below.
		// (i*P*n) / (1 - Math.pow(1+i,-n))
		double tot = 0;
		double tot_numerator = InterestRate*LoanAmount*LoanPaymentCnt;
		double tot_denominator = 1 - Math.pow(1+InterestRate,-LoanPaymentCnt);
		tot = tot_numerator / tot_denominator;
		return tot;
	}

	public double getTotalInterest() {
		// Returns the total interest for the loan. See formula below.
		// I = PRT
		double interest = 0;
		interest = LoanAmount * InterestRate * Time
		return interest;
	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public void setLoanBalanceEnd(double loanBalanceEnd) {
		LoanBalanceEnd = loanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	public int getLoanPaymentCnt() {
		return LoanPaymentCnt;
	}

	public void setLoanPaymentCnt(int loanPaymentCnt) {
		LoanPaymentCnt = loanPaymentCnt;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}

	public void setbCompoundingOption(boolean bCompoundingOption) {
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public void setAdditionalPayment(double additionalPayment) {
		AdditionalPayment = additionalPayment;
	}

	public ArrayList<Payment> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(ArrayList<Payment> loanPayments) {
		this.loanPayments = loanPayments;
	}

	public double getEscrow() {
		return Escrow;
	}

}
