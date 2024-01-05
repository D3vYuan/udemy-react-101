class PaymentInfoRequest {
  amount: number;
  currency: number;
  receiptEmail: string | undefined;

  constructor(
    amount: number,
    currency: number,
    receiptEmail: string | undefined
  ) {
    this.amount = amount;
    this.currency = currency;
    this.receiptEmail = receiptEmail;
  }
}

export default PaymentInfoRequest;
